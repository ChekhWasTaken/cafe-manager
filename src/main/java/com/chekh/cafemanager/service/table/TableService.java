package com.chekh.cafemanager.service.table;

import com.chekh.cafemanager.authorization.entity.CafeUser;
import com.chekh.cafemanager.authorization.service.CafeUserService;
import com.chekh.cafemanager.entity.CafeTable;
import com.chekh.cafemanager.service.error.TableNotFoundException;
import com.chekh.cafemanager.service.table.error.UserIsNotWaiterException;
import com.chekh.cafemanager.service.table.model.Table;
import com.chekh.cafemanager.service.table.model.TableAssignWaiterRequest;
import com.chekh.cafemanager.service.table.model.TableCreateRequest;
import com.chekh.cafemanager.service.table.model.TableGetMyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private CafeUserService userService;

    public boolean tableExists(String id) {
        return tableRepository.existsById(id);
    }


    public CafeTable get(String tableId) {
        return tableRepository.getOne(tableId);
    }

    public TableCreateRequest createTable(TableCreateRequest request) {
        CafeTable cafeTable = tableRepository.save(new CafeTable());

        return new TableCreateRequest(mapEntityToDto(cafeTable));
    }

    public TableGetMyResponse getMyTables() {
        return new TableGetMyResponse(tableRepository.findByWaiter(getCurrentUser()).stream().map(this::mapEntityToDto).collect(Collectors.toList()));
    }

    private CafeUser getCurrentUser() {
        return null;
    }

    private Table mapEntityToDto(CafeTable cafeTable) {
        return new Table(cafeTable.getId());
    }

    public void assignWaiterToTable(TableAssignWaiterRequest request) {
        if (!userService.isWaiter(request.getWaiterId())) {
            throw new UserIsNotWaiterException("Cannot find waiter with id: " + request.getWaiterId());
        }

        if (!tableExists(request.getTableId())) {
            throw new TableNotFoundException("Cannot find table with id: " + request.getTableId());
        }

        CafeTable table = tableRepository.getOne(request.getTableId());

        table.setWaiter(userService.get(request.getWaiterId()));

        tableRepository.save(table);
    }
}
