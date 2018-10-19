package com.chekh.cafemanager.api.table;

import com.chekh.cafemanager.api.InvalidParameterException;
import com.chekh.cafemanager.service.table.TableService;
import com.chekh.cafemanager.service.table.model.TableAssignWaiterRequest;
import com.chekh.cafemanager.service.table.model.TableCreateRequest;
import com.chekh.cafemanager.service.table.model.TableGetMyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class TableController {
    @Autowired
    private TableService tableService;

    @RequestMapping(value = "/table", method = RequestMethod.POST)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TableCreateRequest> createTable(@RequestBody TableCreateRequest request) {
        validateCreateRequest(request);

        return new ResponseEntity<>(tableService.createTable(request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/table", method = RequestMethod.GET)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<TableGetMyResponse> getMyTables() {
        return new ResponseEntity<>(tableService.getMyTables(), HttpStatus.OK);
    }

    @RequestMapping(value = "/table/{tableId}/waiter", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> assignWaiterToTable(@PathVariable String tableId, @RequestBody TableAssignWaiterRequest request) {
        request.setTableId(tableId);

        validateAssignWaiterRequest(request);

        tableService.assignWaiterToTable(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void validateCreateRequest(TableCreateRequest request) {
    }

    private void validateAssignWaiterRequest(TableAssignWaiterRequest request) {
        if (StringUtils.isEmpty(request.getTableId())) {
            throw new InvalidParameterException("Wrong table id");
        }

        if (StringUtils.isEmpty(request.getWaiterId())) {
            throw new InvalidParameterException("Order id should not be empty");
        }
    }
}
