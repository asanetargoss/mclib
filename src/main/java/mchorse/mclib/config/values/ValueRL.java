package mchorse.mclib.config.values;

import com.google.gson.JsonElement;
import mchorse.mclib.client.gui.framework.elements.GuiElement;
import mchorse.mclib.client.gui.framework.elements.buttons.GuiButtonElement;
import mchorse.mclib.client.gui.framework.elements.input.GuiTexturePicker;
import mchorse.mclib.client.gui.framework.elements.utils.GuiLabel;
import mchorse.mclib.client.gui.utils.Elements;
import mchorse.mclib.client.gui.utils.keys.IKey;
import mchorse.mclib.config.gui.GuiConfig;
import mchorse.mclib.utils.resources.RLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ValueRL extends Value
{
	@SideOnly(Side.CLIENT)
	public static GuiTexturePicker picker;

	private ResourceLocation value;
	private ResourceLocation defaultValue;

	public ValueRL(String id, ResourceLocation defaultValue)
	{
		super(id);

		this.defaultValue = defaultValue;
	}

	public ResourceLocation get()
	{
		return this.value;
	}

	public void set(ResourceLocation value)
	{
		this.value = value;
		this.saveLater();
	}

	public void set(String value)
	{
		this.set(RLUtils.create(value));
	}

	@Override
	public void reset()
	{
		this.set(this.defaultValue);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<GuiElement> getFields(Minecraft mc, GuiConfig gui)
	{
		GuiElement element = new GuiElement(mc);
		GuiLabel label = Elements.label(IKey.lang(this.getTitleKey()), 0).anchor(0, 0.5F);
		ValueRL thisValueRL = this;
		// We don't use the lambda shorthand due to it putting an invisible method in this class without the @SideOnly annotation.
		// The anonymous class notation used here causes the creation of a separate .class file, which will be loaded only when needed. :)
		GuiButtonElement pick = new GuiButtonElement(mc, IKey.lang("mclib.gui.pick_texture"), new Consumer<GuiButtonElement>() { @Override public void accept(GuiButtonElement button)
		{
			if (picker == null)
			{
				picker = new GuiTexturePicker(mc, null);
			}

			picker.callback = thisValueRL::set;
			picker.fill(thisValueRL.value);
			picker.flex().relative(gui).wh(1F, 1F);
			picker.resize();

			if (picker.hasParent())
			{
				picker.removeFromParent();
			}

			gui.add(picker);
		}});

		pick.flex().w(90);

		element.flex().row(0).preferred(0).height(20);
		element.add(label, pick);

		return Arrays.asList(element.tooltip(IKey.lang(this.getTooltipKey())));
	}

	@Override
	public void fromJSON(JsonElement element)
	{
		this.value = RLUtils.create(element);
	}

	@Override
	public JsonElement toJSON()
	{
		return RLUtils.writeJson(this.value);
	}
}