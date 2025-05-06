package nn.iamj.borne.modules.commerce.page;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.commerce.unit.CommerceSlot;
import nn.iamj.borne.modules.commerce.unit.CommerceUnit;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommercePage {

    private List<CommerceSlot> unitList = new ArrayList<>();

    public void addUnit(final CommerceSlot slot) {
        this.unitList.add(slot);
    }

    public void addUnit(final CommerceUnit unit) {
        this.unitList.add(CommerceSlot.convertToSlot(unit));
    }

    public void removeUnit(final CommerceSlot slot) {
        this.unitList.remove(slot);
    }

    public void removeUnit(final CommerceUnit unit) {
        this.unitList.remove(CommerceSlot.convertToSlot(unit));
    }

}
