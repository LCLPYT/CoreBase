package work.lclpnet.corebase.asm;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class CoreBaseMixinConnector implements IMixinConnector{

	@Override
	public void connect() {
		System.out.println("Invoking CoreBase Mixin Connectors...");
		Mixins.addConfiguration("mixins.corebase.json");
	}

}
