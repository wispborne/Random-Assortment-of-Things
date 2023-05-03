package assortment_of_things.modular_weapons.effects

import com.fs.starfarer.api.combat.CombatEngineAPI
import com.fs.starfarer.api.combat.CombatEntityAPI
import com.fs.starfarer.api.combat.DamagingProjectileAPI
import com.fs.starfarer.api.combat.ShipAPI
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI
import com.fs.starfarer.api.ui.TooltipMakerAPI
import org.lazywizard.lazylib.MathUtils
import org.lwjgl.util.vector.Vector2f


class OnHitLifesteal : ModularWeaponEffect() {
    override fun getName(): String {
        return "Lifesteal"
    }

    override fun getCost(): Int {
        return 30
    }

    override fun getIcon(): String {
        return ""
    }

    override fun getTooltip(tooltip: TooltipMakerAPI) {
        tooltip.addPara("On hull-hit, 1/5 of hull damage dealt is converted in to repaired hull for the firing ship. A ship can only ever recover up to 50% of its own hull per combat session through this effect.", 0f)
    }

    override fun getResourceCost(): MutableMap<String, Float> {
        return hashMapOf()
    }

    override fun getType(): ModularEffectModifier {
        return ModularEffectModifier.Onhit
    }


    override fun onHit(projectile: DamagingProjectileAPI?, target: CombatEntityAPI?, point: Vector2f?, shieldHit: Boolean, damageResult: ApplyDamageResultAPI?, engine: CombatEngineAPI?) {
        super.onHit(projectile, target, point, shieldHit, damageResult, engine)

        if (shieldHit) return
        if (target !is ShipAPI) return

        var ship = projectile!!.weapon.ship
        var recovered = ship.customData.get("rat_modular_lifesteal_recovered") as Float?
        if (recovered == null)
        {
            recovered = 0f
        }

        if (recovered < ship.maxHitpoints / 2 && ship.hitpoints < ship.maxHitpoints)
        {

            var recover = MathUtils.clamp(damageResult!!.damageToHull, 0f, ship.maxHitpoints - ship.hitpoints)
            ship.hitpoints += recover


            recovered+= recover
            ship.setCustomData("rat_modular_lifesteal_recovered", recovered)
        }
    }



}