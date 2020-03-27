/**
 * @NApiVersion 2.x
 * @NScriptType UserEventScript
 * @NModuleScope Public
 */

define(['N/search', 'N/record'], function (search, record) {
    function getItemTypes(itemIds) {
        var itemSearch = search.create({
            type: 'item',
            filters: [['internalid', 'anyof', itemIds]],
            columns: ['usebins']
        });

        var pages = itemSearch.runPaged({pageSize: 1000});

        var lotItems = [];
        var lotItemsNoBins = [];
        var invItems = [];
        var invNoBinsItems = [];
        pages.pageRanges.forEach(function (page) {
            try {
                var actualPage = pages.fetch({index: page.index});
                actualPage.data.forEach(function (row) {
                    if (/lot/.test(row.recordType) && row.getValue({name: 'usebins'})) {
                        lotItems.push(row.id);
                    } else if (/lot/.test(row.recordType)) {
                        lotItemsNoBins.push(row.id);
                    } else if (row.getValue({name: 'usebins'})) {
                        invItems.push(row.id);
                    } else {
                        invNoBinsItems.push(row.id);
                    }
                });
            } catch (ex) {
                log.debug('getItemTypes: Error while recovering values from Inv Detail Numbers', ex.message);
            }
        });

        return {lotItems: lotItems, lotItemsNoBins: lotItemsNoBins, invItems: invItems, invNoBinsItems: invNoBinsItems};
    }

    function getLotItemsData(itemIds, location) {
        if (!itemIds.length) {
            return {};
        }

        var filters = [["internalid", "anyof", itemIds], "AND", ["inventorynumberbinonhand.quantityavailable", "greaterthan", "0"], "AND", ["formulatext: REGEXP_SUBSTR({inventorynumberbinonhand.inventorynumber}, {inventorynumber.inventorynumber})", "isnotempty", ""],
            'AND', ['inventorynumberbinonhand.location', 'anyof', location]];
        var columns = [];

        columns.push(search.createColumn({name: 'expirationdate', join: 'inventoryNumber', sort: search.Sort.ASC}));
        columns.push(search.createColumn({name: 'quantityavailable', join: 'inventoryNumberBinOnHand', sort: search.Sort.ASC}));
        columns.push(search.createColumn({name: 'inventorynumber', join: 'inventoryNumberBinOnHand'}));
        columns.push(search.createColumn({name: 'binnumber', join: 'inventoryNumberBinOnHand'}));

        var preferredBinSearch = search.create({type: 'item', filters: filters, columns: columns});
        var pages = preferredBinSearch.runPaged({pageSize: 1000});

        var allInvNumbers = {};
        pages.pageRanges.forEach(function (page) {
            try {
                var actualPage = pages.fetch({index: page.index});
                actualPage.data.forEach(function (row) {
                    var tempObj = {
                        id: row.id,
                        invnumberId: row.getValue({name: 'inventorynumber', join: 'inventoryNumberBinOnHand'}),
                        quantityonhand: row.getValue({name: 'quantityavailable', join: 'inventoryNumberBinOnHand'}),
                        binnumber: row.getValue({name: 'binnumber', join: 'inventoryNumberBinOnHand'}),
                        binnumberText: row.getText({name: 'binnumber', join: 'inventoryNumberBinOnHand'}),
                        expirationdate: row.getValue({name: 'expirationdate', join: 'inventoryNumber'}),
                    };

                    allInvNumbers[tempObj.id] = allInvNumbers[tempObj.id] || [];
                    allInvNumbers[ tempObj.id ].push(tempObj);
                });
            } catch (ex) {
                log.debug('getLotItemsData - Error en Fetch', ex);
            }
        });

        return allInvNumbers;
    }

    function getLotItemsNoBinsData(itemIds, location) {
        if (!itemIds.length) {
            return {};
        }

        var filters = [["internalid", "anyof", itemIds], "AND", ["inventorynumber.location", "anyof", location], 'AND', ['inventorynumber.quantityavailable', 'greaterthan', 0]];

        var columns = [];
        columns.push(search.createColumn({name: 'expirationdate', join: 'inventoryNumber', sort: search.Sort.ASC}));
        columns.push(search.createColumn({name: 'quantityavailable', join: 'inventoryNumber', sort: search.Sort.ASC}));
        columns.push(search.createColumn({name: 'inventorynumber', join: 'inventoryNumber'}));
        columns.push(search.createColumn({name: 'internalid', join: 'inventoryNumber'}));

        var preferredBinSearch = search.create({type: 'item', filters: filters, columns: columns});
        var pages = preferredBinSearch.runPaged({pageSize: 1000});

        var allInvNumbers = {};
        pages.pageRanges.forEach(function (page) {
            try {
                var actualPage = pages.fetch({index: page.index});
                actualPage.data.forEach(function (row) {
                    var tempObj = {
                        id: row.id,
                        invnumberId: row.getValue({name: 'internalid', join: 'inventoryNumber'}),
                        invnumberName: row.getValue({name: 'inventorynumber', join: 'inventoryNumber'}),
                        quantityonhand: row.getValue({name: 'quantityavailable', join: 'inventoryNumber'}),
                        expirationdate: row.getValue({name: 'expirationdate', join: 'inventoryNumber'}),
                    };

                    allInvNumbers[tempObj.id] = allInvNumbers[tempObj.id] || [];
                    allInvNumbers[ tempObj.id ].push(tempObj);
                });
            } catch (ex) {
                log.debug('getNotLotItemsData - Error en Fetch', ex);
            }
        });

        return allInvNumbers;
    }

    function getInvItemsData(itemIds, location) {
        if (!itemIds.length) {
            return {};
        }

        var filters = [["internalid", "anyof", itemIds], "AND", ["binnumber.location", "anyof", location], 'AND', ['binonhandavail', 'greaterthan', 0]];

        var columns = [];
        columns.push(search.createColumn({name: 'binnumber'}));
        columns.push(search.createColumn({name: 'internalid', join: 'binnumber'}));
        columns.push(search.createColumn({name: 'binonhandavail'}));

        var preferredBinSearch = search.create({type: 'item', filters: filters, columns: columns});
        var pages = preferredBinSearch.runPaged({pageSize: 1000});

        var allInvNumbers = {};
        pages.pageRanges.forEach(function (page) {
            try {
                var actualPage = pages.fetch({index: page.index});
                actualPage.data.forEach(function (row) {
                    var tempObj = {
                        id: row.id,
                        binNumberText: row.getValue({name: 'binnumber'}),
                        binnumber: row.getValue({name: 'internalid', join: 'binnumber'}),
                        quantityonhand: row.getValue({name: 'binonhandavail'}),
                    };

                    allInvNumbers[tempObj.id] = allInvNumbers[tempObj.id] || [];
                    allInvNumbers[ tempObj.id ].push(tempObj);
                });
            } catch (ex) {
                log.debug('getInvItemsData - Error en Fetch', ex);
            }
        });

        return allInvNumbers;
    }

    function makeInvDetail(objSubRecord, itemsData, itemId, quantity, usebins, uselot) {
        objSubRecord.setValue({fieldId: 'quantity', value: quantity});

        log.audit('itemId', itemId);
        for (; quantity > 0; ) {
            if (!itemsData.hasOwnProperty(itemId) || !itemsData[itemId].length) {
                throw 'No hay inventario suficiente para surtir la orden';
            }

            var currentDetail = itemsData[itemId][0];
            //
            /// Los  bins
                                //Something (Extended Mix)
                                //Artista
                                //Lasgo
                                ////
            
            objSubRecord.selectNewLine({sublistId: 'inventoryassignment'});

            if (uselot) {
                objSubRecord.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'issueinventorynumber', value: currentDetail.invnumberId});
            }

            if (usebins) {
                objSubRecord.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'binnumber', value: currentDetail.binnumber});
            }

            if (currentDetail.quantityonhand <= Math.abs(quantity)) {
                var parsedQty = (currentDetail.quantityonhand + '').substr(0, 10);
                //log.audit('currentDetail.quantityonhand', currentDetail.quantityonhand);
                objSubRecord.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'quantity', value: parsedQty});
                quantity -= currentDetail.quantityonhand;
                itemsData[itemId].shift();
            } else {
                var parsedQty = (quantity + '').substr(0, 10);
                //log.audit('quantity', quantity);
                objSubRecord.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'quantity', value: parsedQty});
                currentDetail.quantityonhand -= quantity;
                quantity = 0;
            }

            objSubRecord.commitLine({sublistId: 'inventoryassignment'});
        }
    }

    function assemblyBuild(item, peso, lote, location, fechaCaducidad) {
        var assembly = record.create({type: 'assemblybuild', isDynamic: true});
        assembly.setValue({fieldId: 'subsidiary', value: 3});
        assembly.setValue({fieldId: 'item', value: item});
        assembly.setValue({fieldId: 'quantity', value: peso});
        assembly.setValue({fieldId: 'location', value: location});

        var detail = assembly.getSubrecord({fieldId: 'inventorydetail'});
        detail.setValue({fieldId: 'quantity', value: peso});

        detail.selectNewLine({sublistId: 'inventoryassignment'});
        detail.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'receiptinventorynumber', value: lote});
        if (fechaCaducidad) {
            detail.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'expirationdate', value: fechaCaducidad});
        }

        detail.setCurrentSublistValue({sublistId: 'inventoryassignment', fieldId: 'quantity', value: peso});
        detail.commitLine({sublistId: 'inventoryassignment'});

        var components = (function () {
            var result = [];
            for (var i = 0, l = assembly.getLineCount('component'); i < l; ++i) {
                var component = assembly.getSublistValue({sublistId: 'component', fieldId: 'item', line: i});
                result.push(component);
            }
            return result;
        })();

        var itemTypes = getItemTypes(components);
        var lotItems = getLotItemsData(itemTypes.lotItems, location);
        var lotItemsNoBins = getLotItemsNoBinsData(itemTypes.lotItemsNoBins, location);
        var invItems = getInvItemsData(itemTypes.invItems, location);

        for (var i = 0, l = assembly.getLineCount('component'); i < l; ++i) {
            var component = assembly.getSublistValue({sublistId: 'component', fieldId: 'item', line: i});
            var qty = assembly.getSublistValue({sublistId: 'component', fieldId: 'quantity', line: i});

            assembly.selectLine({sublistId: 'component', line: i});

            if (lotItems[component]) {
                var objSubRecord = assembly.getCurrentSublistSubrecord({sublistId: 'component', fieldId: 'componentinventorydetail'});
                makeInvDetail(objSubRecord, lotItems, component, qty, true, true);
            }
            if (lotItemsNoBins[component]) {
                var objSubRecord = assembly.getCurrentSublistSubrecord({sublistId: 'component', fieldId: 'componentinventorydetail'});
                makeInvDetail(objSubRecord, lotItemsNoBins, component, qty, false, true);
            }
            if (invItems[component]) {
                var objSubRecord = assembly.getCurrentSublistSubrecord({sublistId: 'component', fieldId: 'componentinventorydetail'});
                makeInvDetail(objSubRecord, invItems, component, qty, true, false);
            }

            assembly.commitLine({sublistId: 'component'});
        }

        return assembly.save();
    }

    function fillInventoryNumberRecord(assemblyId, piezas, cajas, lote, vigFresco, vigCongelado, fechaProd) {
        var assembly = record.load({type: 'assemblybuild', id: assemblyId});
        var detail = assembly.getSubrecord({fieldId: 'inventorydetail'});

        var numberedrecordid = detail.getSublistValue({sublistId: 'inventoryassignment', fieldId: 'numberedrecordid', line: 0});

        var esCajas = parseFloat(cajas) || 0;

        var inventoryNumber = record.load({type: 'inventorynumber', id: numberedrecordid});

        inventoryNumber.setValue({fieldId: 'custitemnumber_qua_dtb_cantidad_pzas', value: piezas});
        inventoryNumber.setValue({fieldId: 'custitemnumberqua_dtb_cantidad_cajas', value: cajas});
        inventoryNumber.setValue({fieldId: 'custitemnumber_qua_dtb_num_caja', value: lote});
        inventoryNumber.setValue({fieldId: 'custitemnumber_qua_dtb_tipo_prod', value: (esCajas ? 2 : 1)});

        if (fechaProd) {
            inventoryNumber.setValue({fieldId: 'custitemnumber_date', value: fechaProd});
        }

        if (vigFresco) {
            inventoryNumber.setValue({fieldId: 'custitemnumber_qua_dtb_fecha_vig_fresco', value: vigFresco});
        }

        if (vigCongelado) {
            inventoryNumber.setValue({fieldId: 'custitemnumber_qua_dtb_fecha_vig_congelado', value: vigCongelado});
        }

        return inventoryNumber.save();
    }

    function afterSubmit(context) {
        if (context.type != 'edit' && context.type != 'create') {
            return;
        }

        var newRecord = context.newRecord;

        if (newRecord.getValue({fieldId: 'custrecord_cpp_ensamble_construido'})) {
            return;
        }

        var item = newRecord.getValue({fieldId: 'custrecord_cpp_articulo'});
        var location = 1;
        if (!item) {
            return;
        }

        var peso = newRecord.getValue({fieldId: 'custrecord_cpp_peso'});
        var lote = newRecord.getValue({fieldId: 'custrecord_cpp_lote'});
        var piezas = newRecord.getValue({fieldId: 'custrecord_cpp_cantidad_piezas'});
        var cajas = newRecord.getValue({fieldId: 'custrecord_cpp_cantidad_cajas'});

        var fechaProd = newRecord.getValue({fieldId: 'custrecord_cpp_fecha'});
        var vigFresco = newRecord.getValue({fieldId: 'custrecord_cpp_fecha_caducidad_fresco'});
        var vigCongelado = newRecord.getValue({fieldId: 'custrecord_cpp_fecha_caducidad_congelado'});

        try {
            var assemblyId = assemblyBuild(item, peso, lote, location, vigFresco);
            var inventoryNumberId = fillInventoryNumberRecord(assemblyId, piezas, cajas, lote, vigFresco, vigCongelado, fechaProd);

            log.audit('inventoryNumberId', inventoryNumberId);

            record.submitFields({
                type: newRecord.type,
                id: newRecord.id,
                values: {custrecord_cpp_ensamble_construido: assemblyId}
            });
        } catch (e) {
            log.error(e.name, e.message);
        }
    }

    return {
        afterSubmit: afterSubmit
    };
});