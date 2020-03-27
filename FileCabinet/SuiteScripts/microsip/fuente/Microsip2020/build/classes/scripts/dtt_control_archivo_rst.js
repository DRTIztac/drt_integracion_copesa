/**
 * @NApiVersion 2.x
 * @NScriptType Restlet
 * @NModuleScope Public
 */
define(['N/record', 'N/runtime', 'N/search', 'N/log', 'N/format', 'N/encode'],
    /**
     * @param {http} http
     * @param {record} record
     * @param {runtime} runtime
     * @param {search} search
     * Jorge  Villalobos
     * 2019  -  Noviembre.
     * Aqui  se llebaran todos los  proceso...
     */
    function (recordAPI, runtime, searchAPI, logAPI, format, encode) {
        var error = "";
        var allInvNumbers = [];

        function procesadoTx(docto_pv_id) {
            var id = 0;
            var filters = [
                ["custrecord_dtt_docto_pv_id", "equalto", docto_pv_id]
                ,'and',
                ['isinactive', 'is', false]
            ];
            var columns = [];
            columns.push(searchAPI.createColumn({
                name: 'internalid'
            }));
            var mySearch = searchAPI.create({
                type: 'customrecord_mx_microsip_log',
                filters: filters,
                columns: columns
            });
            mySearch.run().each(function (result) {
                id = result.getValue({
                    name: 'internalid'
                });
                return true;
            });
            return id;
        }



        function creaCliente(cliente) {
            var objRecordT = recordAPI.create({
                type: 'customer'
            });
            var id = 0;
            try {
                objRecordT.setValue({
                    fieldId: 'companyname',
                    value: cliente
                });
                objRecordT.setValue({
                    fieldId: 'subsidiary',
                    value: 3
                });
                id = objRecordT.save();
                log.debug("id Cliente", id);
                id = parseInt(id + "");
            } catch (e) {
                log.error("Cliente alta", JSON.stringify(e));
            }
            return id;
        }

        function buscarCliente(cliente) {
            var id = 0;
            var filtros = ['companyname', 'is', cliente];
            var recordSearch = searchAPI.create({
                type: 'customer',
                filters: filtros,
                columns: [
                    'internalid',
                ]
            });
            recordSearch.run().each(function (row) {
                id = row.getValue('internalid');
                return true;
            });

            return id;
        }

        function doPost(datain) {
            // Microsip datos
            // CHILES SECOS JR.FDB
            logAPI.debug("data", datain);
            logAPI.debug("accion", datain.accion);


            if (datain.accion == "entrada") {
                return {
                    ok: 'ok',
                    error: ''
                };
            }

            if (datain.accion == "item") {
                var resultados = [];
                var filter = [
                    ['isinactive', 'is', false]
                ];

                var mySearch = searchAPI.create({
                    type: searchAPI.Type.ITEM,
                    filters: filter,
                    columns: [{
                            'name': 'internalid',
                            'sort': 'DESC'
                        },
                        'displayname', 'baseprice'
                    ]
                });

                mySearch.run().each(function (result) {
                    var internalid = result.getValue({
                        name: 'internalid'
                    });
                    var displayname = result.getValue({
                        name: 'displayname'
                    });
                    var baseprice = result.getValue({
                        name: 'baseprice'
                    });

                    resultados.push({
                        internalid: internalid,
                        displayname: displayname,
                        baseprice: baseprice
                    });
                    return true;
                });
                log.audit({
                    title: 'resultados',
                    details: JSON.stringify(resultados)
                });
                return {
                    ok: 'ok',
                    error: '',
                    resultados: resultados
                };
            }


            if (datain.accion == "produccion2") {
                var error = "";
                var idReg = "";
                var idCliente = 0;
                var cliente = "";
                try {
                    var jsonTxt = encode.convert({
                        string: datain.data,
                        inputEncoding: encode.Encoding.BASE_64,
                        outputEncoding: encode.Encoding.UTF_8
                    });
                    // log.debug("jsonTxt",jsonTxt );
                    var jsonTrabajo = JSON.parse(jsonTxt);
                    idCliente = jsonTrabajo.cliente_id;
                    cliente = jsonTrabajo.cliente;
                    var venta_id = jsonTrabajo.venta_id;
                    log.debug("idCliente", idCliente);
                    log.debug("cliente", cliente);
                    log.debug("venta_id", venta_id);
                    var itemArr = jsonTrabajo.item[0];
                    log.debug("item", itemArr);
                    jsonTrabajo.item = itemArr;
                    if (idCliente == 0) {
                        idCliente = buscarCliente(cliente);
                    }
                    if (idCliente == 0) {
                        idCliente = creaCliente(cliente);
                    }
                    if (idCliente > 0) {
                        var idRegHecho = procesadoTx(venta_id);
                        if (idRegHecho == 0) {
                            var objRecordT = recordAPI.create({
                                type: 'customrecord_mx_microsip_log'
                            });
                            objRecordT.setValue({
                                fieldId: 'custrecord_dtt_docto_pv_id',
                                value: venta_id
                            });
                            objRecordT.setValue({
                                fieldId: 'custrecord_dtt_fecha_tx',
                                value: jsonTrabajo.fecha
                            });

                            objRecordT.setValue({
                                fieldId: 'custrecord_dtt_hora_tx',
                                value: jsonTrabajo.hora
                            });

                            objRecordT.setValue({
                                fieldId: 'custrecord_dtt_json_upload',
                                value: jsonTxt
                            });
                            objRecordT.setValue({
                                fieldId: 'custrecord_dtt_mx_cliente',
                                value: idCliente
                            });
                            idReg = objRecordT.save();
                        } else {
                            error = "Ya  se proceso " + idRegHecho;
                        }
                    }
                } catch (e) {
                    log.error("creando registro", JSON.stringify(e));
                    error = JSON.stringify(e);
                }
                return {
                    idReg: idReg,
                    error: error
                }
            }


            if (datain.accion == "customer") {
                var resultados = [];
                var filter = [
                    ['isinactive', 'is', false]
                ];

                var mySearch = searchAPI.create({
                    type: 'customer',
                    filters: filter,
                    columns: [{
                            'name': 'internalid',
                            'sort': 'DESC'
                        },
                        'companyname'
                    ]
                });

                mySearch.run().each(function (result) {
                    var internalid = result.getValue({
                        name: 'internalid'
                    });
                    var companyname = result.getValue({
                        name: 'companyname'
                    });
                    resultados.push({
                        internalid: internalid,
                        companyname: companyname
                    });
                    return true;
                });
                return {
                    ok: 'ok',
                    error: '',
                    resultados: resultados
                };
            }
        }

        return {
            post: doPost
        };

    })