package lv.ctco.cukesrest.gadgets;

import lv.ctco.cukesrest.common.InMemoryStorage;
import lv.ctco.cukesrest.gadgets.dto.GadgetDto;
import lv.ctco.cukesrest.gadgets.dto.GadgetType;

import javax.inject.Inject;
import java.util.*;

public class DummyGadgetService {

    @Inject
    InMemoryStorage storage;

    public Collection<GadgetDto> searchGadgets() {
        return storage.getGadgets().values();
    }

    public GadgetDto getGadget(Integer id) {
        return storage.getGadgets().get(id);
    }

    public Integer addGadget(GadgetDto gadget) {
        if (gadget == null || !isValidType(gadget)) return null;

        Set<Integer> gadgetIds = storage.getGadgets().keySet();
        Integer newId = Collections.max(gadgetIds) + 1;
        gadget.setId(newId);
        gadget.setUpdatedDate(null);
        gadget.setCreatedDate(new Date());

        storage.getGadgets().put(newId, gadget);
        return newId;
    }

    public boolean updateGadget(Integer id, GadgetDto updated) {
        GadgetDto gadget = storage.getGadgets().get(id);
        if (gadget == null || updated == null || !isValidType(gadget)) return false;

        gadget.setName(updated.getName());
        gadget.setType(updated.getType());
        gadget.setOwner(updated.getOwner());
        gadget.setUpdatedDate(new Date());
        return true;
    }

    public boolean removeGadget(Integer id) {
        Map<Integer, GadgetDto> gadgets = storage.getGadgets();
        GadgetDto gadget = gadgets.get(id);
        if (gadget != null) {
            gadgets.remove(id);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidType(GadgetDto gadget) {
        GadgetType type = gadget.getType();
        return type != null && type != GadgetType.BOOK_READER;
    }
}
