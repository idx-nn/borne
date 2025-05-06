package nn.iamj.borne.modules.commerce;

import nn.iamj.borne.modules.commerce.page.CommercePage;

import java.util.List;

public interface Commerce {

    void addPage(final CommercePage page);
    CommercePage getPage(final int i);
    void removePage(final CommercePage page);

    List<CommercePage> getPages();
    void clearPages();

}
