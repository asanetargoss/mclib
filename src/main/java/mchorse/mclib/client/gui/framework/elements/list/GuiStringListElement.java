package mchorse.mclib.client.gui.framework.elements.list;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import mchorse.mclib.McLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiStringListElement extends GuiListElement<String>
{
    public GuiStringListElement(Minecraft mc, Consumer<List<String>> callback)
    {
        super(mc, callback);

        this.scroll.scrollItemSize = 16;
    }

    @Override
    protected boolean sortElements()
    {
        Collections.sort(this.list);

        return true;
    }

    @Override
    public void drawElement(String element, int i, int x, int y, boolean hover, boolean selected)
    {
        if (selected)
        {
            Gui.drawRect(x, y, x + this.scroll.w, y + this.scroll.scrollItemSize, 0x88000000 + McLib.primaryColor.get());
        }

        this.font.drawStringWithShadow(element, x + 4, y + 4, hover ? 16777120 : 0xffffff);
    }
}