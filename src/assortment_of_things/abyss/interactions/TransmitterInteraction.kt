package assortment_of_things.abyss.interactions

import assortment_of_things.abyss.AbyssUtils
import assortment_of_things.abyss.procgen.AbyssDepth
import assortment_of_things.misc.RATInteractionPlugin
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec.*
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity

import com.fs.starfarer.api.loading.Description
import org.magiclib.kotlin.fadeAndExpire
import org.magiclib.kotlin.getSalvageSeed
import java.util.*
import kotlin.collections.ArrayList

class TransmitterInteraction : RATInteractionPlugin() {

    override fun init() {

        if (AbyssUtils.isAnyFleetTargetingPlayer())
        {
            textPanel.addPara("As there are currently hostile targets following the fleets steps, safe docking at the station seems impossible.")
            addLeaveOption()
            return
        }

        textPanel.addPara("Your fleet approaches the transmitter.")

        textPanel.addPara(Global.getSettings().getDescription(interactionTarget.customDescriptionId, Description.Type.CUSTOM).text1)


        createOption("Investigate") {
            clearOptions()
            textPanel.addPara("The fleet inspects the transmitter. It appears to still be sending out data infrequently, but cant establish a connection to anything else. Proceeding with a salvage operation may allow us to retrieve some of the data it has collected.")

            createOption("Begin salvage operations") {
                clearOptions()
                var random = Random(interactionTarget.getSalvageSeed())
                var depth = AbyssUtils.getSystemData(interactionTarget.starSystem).depth

                var dropRandom = ArrayList<DropData>()
                var dropValue = ArrayList<DropData>()
                var drop = DropData()


                drop = DropData()
                drop.group = "basic"
                drop.value = 3000
                dropValue.add(drop)




                drop = DropData()
                drop.chances = 2
                drop.group = "rat_abyss_transmitter"
                dropRandom.add(drop)

                drop = DropData()
                drop.chances = 1
                drop.group = "any_hullmod_medium"
                dropRandom.add(drop)

                var mult = when(depth) {
                    AbyssDepth.Shallow -> 1f
                    AbyssDepth.Deep -> 2f
                    else -> 1f
                }

                var salvage = SalvageEntity.generateSalvage(random, mult, 1f, 1f, 1f, dropValue, dropRandom)

                visualPanel.showLoot("Loot", salvage, true) {
                    closeDialog()

                    interactionTarget.fadeAndExpire(3f)
                }
            }

            addLeaveOption()

        }

        addLeaveOption()

    }
}