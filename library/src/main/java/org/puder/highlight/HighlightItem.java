package org.puder.highlight;

public class HighlightItem {

    protected int              titleId;
    protected int              descriptionId;


    protected HighlightItem() {
    }

    public HighlightItem setTitle(int id) {
        this.titleId = id;
        return this;
    }

    public HighlightItem setDescriptionId(int id) {
        this.descriptionId = id;
        return this;
    }
}
