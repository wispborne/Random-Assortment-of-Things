package assortment_of_things.abyss.shipsystem.ai

import assortment_of_things.abyss.hullmods.abyssals.AbyssalsCoreHullmod
import com.fs.starfarer.api.combat.*
import com.fs.starfarer.api.combat.ShipwideAIFlags.AIFlags
import org.lwjgl.util.vector.Vector2f

class AbolethSystemAI : ShipSystemAIScript {

    var ship: ShipAPI? = null

    override fun init(ship: ShipAPI?, system: ShipSystemAPI?, flags: ShipwideAIFlags?, engine: CombatEngineAPI?) {
        this.ship = ship
    }

    override fun advance(amount: Float, missileDangerDir: Vector2f?, collisionDangerDir: Vector2f?, target: ShipAPI?) {
        if (ship == null) return
        var flags = ship!!.aiFlags
        var system = ship!!.system
        if (system.isCoolingDown) return
        if (system.isActive) {
            return
        }

        if (ship!!.fluxLevel > 0.7f) {
            ship!!.useSystem()
            return
        }

        if (AbyssalsCoreHullmod.isChronosCore(ship!!))
        {
            var validFlags = listOf<AIFlags>(AIFlags.PURSUING, AIFlags.MANEUVER_TARGET, AIFlags.MOVEMENT_DEST, AIFlags.RUN_QUICKLY)
            for (valid in validFlags)
            {
                if (flags.hasFlag(valid)) {
                    ship!!.useSystem()
                    return
                }
            }
        }

        if (AbyssalsCoreHullmod.isCosmosCore(ship!!))
        {
            var validFlags = listOf<AIFlags>(AIFlags.PURSUING, AIFlags.BACK_OFF,  AIFlags.RUN_QUICKLY, AIFlags.BACKING_OFF)
            for (valid in validFlags)
            {
                if (flags.hasFlag(valid)) {
                    ship!!.useSystem()
                    return
                }
            }
        }
    }
}