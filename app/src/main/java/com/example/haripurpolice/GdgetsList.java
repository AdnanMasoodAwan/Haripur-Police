package com.example.haripurpolice;

public class GdgetsList
{
    private final String Gadgetame;
    private final int GadgetIcon;

    public GdgetsList(String gadgetame, int gadgetIcon)
    {
        Gadgetame = gadgetame;
        GadgetIcon = gadgetIcon;
    }


    public String getGadgetame() {
        return Gadgetame;
    }

    public int getGadgetIcon() {
        return GadgetIcon;
    }
}
