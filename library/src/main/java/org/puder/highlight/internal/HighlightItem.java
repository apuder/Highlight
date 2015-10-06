package org.puder.highlight.internal;

public class HighlightItem {

    protected int titleId;
    protected int descriptionId;

    protected int screenLeft;
    protected int screenTop;
    protected int screenRight;
    protected int screenBottom;


    protected HighlightItem() {
        titleId = -1;
        descriptionId = -1;
    }

    public HighlightItem setTitle(int id) {
        this.titleId = id;
        return this;
    }

    public HighlightItem setDescriptionId(int id) {
        this.descriptionId = id;
        return this;
    }

    public void setScreenPosition(int screenLeft, int screenTop, int screenRight, int screenBottom) {
        this.screenLeft = screenLeft;
        this.screenTop = screenTop;
        this.screenRight = screenRight;
        this.screenBottom = screenBottom;
    }
}
