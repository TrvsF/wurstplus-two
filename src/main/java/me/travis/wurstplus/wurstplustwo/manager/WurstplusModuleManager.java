package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickGUI;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickHUD;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.*;
import me.travis.wurstplus.wurstplustwo.hacks.combat.*;
import me.travis.wurstplus.wurstplustwo.hacks.dev.WurstplusFakePlayer;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.*;
import me.travis.wurstplus.wurstplustwo.hacks.misc.*;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusSprint;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStep;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStrafe;
import me.travis.wurstplus.wurstplustwo.hacks.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;

public class WurstplusModuleManager {

	public static ArrayList<WurstplusHack> array_hacks = new ArrayList<>();

	public static Minecraft mc = Minecraft.getMinecraft();

	public WurstplusModuleManager() {

		// CLick GUI and HUD.
		add_hack(new WurstplusClickGUI());
		add_hack(new WurstplusClickHUD());

		// Chat.
		add_hack(new WurstplusChatSuffix());
		add_hack(new WurstplusVisualRange());
		add_hack(new WurstplusTotempop());
		add_hack(new WurstplusClearChat());
		add_hack(new WurstplusChatMods());
		add_hack(new WurstplusAutoEz());
		add_hack(new WurstplusAntiRacist());
		add_hack(new WurstplusAnnouncer());

		// Combat.
		add_hack(new WurstplusCriticals());
		add_hack(new WurstplusKillAura());
		add_hack(new WurstplusSurround());
		add_hack(new WurstplusVelocity());
		add_hack(new WurstplusAutoCrystal());
		add_hack(new WurstplusHoleFill());
		add_hack(new WurstplusTrap());
		add_hack(new WurstplusSocks());
		add_hack(new WurstplusSelfTrap());
		add_hack(new WurstplusAutoArmour());
		add_hack(new WurstplusAuto32k());
		add_hack(new WurstplusWebfill());
		add_hack(new WurstplusAutoWeb());
		add_hack(new WurstplusBedAura());
		add_hack(new WurstplusOffhand());
		add_hack(new WurstplusAutoGapple());
		add_hack(new WurstplusAutoTotem());
		add_hack(new WurstplusAutoMine());

		// Exploit.
		add_hack(new WurstplusXCarry());
		add_hack(new WurstplusNoSwing());
		add_hack(new WurstplusPortalGodMode());
		add_hack(new WurstplusPacketMine());
		add_hack(new WurstplusEntityMine());
		add_hack(new WurstplusBuildHeight());
		add_hack(new WurstplusCoordExploit());
		add_hack(new WurstplusNoHandshake());

		// Movement.
		add_hack(new WurstplusStrafe());
		add_hack(new WurstplusStep());
		add_hack(new WurstplusSprint());

		// Render.
		add_hack(new WurstplusHighlight());
		add_hack(new WurstplusHoleESP());
		add_hack(new WurstplusShulkerPreview());
		add_hack(new WurstplusViewmodleChanger());
		add_hack(new WurstplusVoidESP());
		add_hack(new WurstplusAntifog());
		add_hack(new WurstplusNameTags());
		add_hack(new WurstplusFuckedDetector());
		add_hack(new WurstplusTracers());
		add_hack(new WurstplusSkyColour());
		add_hack(new WurstplusChams());
		add_hack(new WurstplusCapes());
		add_hack(new WurstplusAlwaysNight());
		add_hack(new WurstplusCityEsp());

		// Misc.
		add_hack(new WurstplusMiddleClickFriends());
		add_hack(new WurstplusStopEXP());
		add_hack(new WurstplusAutoReplenish());
		add_hack(new WurstplusAutoNomadHut());
		add_hack(new WurstplusFastUtil());
		add_hack(new WurstplusSpeedmine());

		// Dev
		add_hack(new WurstplusFakePlayer());

		array_hacks.sort(Comparator.comparing(WurstplusHack::get_name));
	}

	public void add_hack(WurstplusHack module) {
		array_hacks.add(module);
	}

	public ArrayList<WurstplusHack> get_array_hacks() {
		return array_hacks;
	}

	public ArrayList<WurstplusHack> get_array_active_hacks() {
		ArrayList<WurstplusHack> actived_modules = new ArrayList<>();

		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.is_active()) {
				actived_modules.add(modules);
			}
		}

		return actived_modules;
	}

	public Vec3d process(Entity entity, double x, double y, double z) {
		return new Vec3d(
			(entity.posX - entity.lastTickPosX) * x,
			(entity.posY - entity.lastTickPosY) * y,
			(entity.posZ - entity.lastTickPosZ) * z);
	}

	public Vec3d get_interpolated_pos(Entity entity, double ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(process(entity, ticks, ticks, ticks)); // x, y, z.
	}

	public void render(RenderWorldLastEvent event) {
		mc.profiler.startSection("wurstplus");
		mc.profiler.startSection("setup");

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableDepth();

		GlStateManager.glLineWidth(1f);

		Vec3d pos = get_interpolated_pos(mc.player, event.getPartialTicks());

		WurstplusEventRender event_render = new WurstplusEventRender(RenderHelp.INSTANCE, pos);

		event_render.reset_translation();

		mc.profiler.endSection();

		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.is_active()) {
				mc.profiler.startSection(modules.get_tag());

				modules.render(event_render);

				mc.profiler.endSection();
			}
		}

		mc.profiler.startSection("release");

		GlStateManager.glLineWidth(1f);

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.enableCull();

		RenderHelp.release_gl();

		mc.profiler.endSection();
		mc.profiler.endSection();
	}

	public void update() {
		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.is_active()) {
				modules.update();
			}
		}
	}

	public void render() {
		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.is_active()) {
				modules.render();
			}
		}
	}

	public void bind(int event_key) {
		if (event_key == 0) {
			return;
		}

		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.get_bind(0) == event_key) {
				modules.toggle();
			}
		}
	}

	public WurstplusHack get_module_with_tag(String tag) {
		WurstplusHack module_requested = null;

		for (WurstplusHack module : get_array_hacks()) {
			if (module.get_tag().equalsIgnoreCase(tag)) {
				module_requested = module;
			}
		}

		return module_requested;
	}

	public ArrayList<WurstplusHack> get_modules_with_category(WurstplusCategory category) {
		ArrayList<WurstplusHack> module_requesteds = new ArrayList<>();

		for (WurstplusHack modules : get_array_hacks()) {
			if (modules.get_category().equals(category)) {
				module_requesteds.add(modules);
			}
		}

		return module_requesteds;
	}

}