package assortment_of_things.abyss.items.cores.officer

import assortment_of_things.abyss.items.cores.AICoreUtil
import assortment_of_things.abyss.skills.TimeCoreSkill
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.AICoreOfficerPlugin
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.impl.campaign.ids.Personalities
import com.fs.starfarer.api.impl.campaign.ids.Ranks
import com.fs.starfarer.api.impl.campaign.ids.Skills
import com.fs.starfarer.api.loading.Description
import com.fs.starfarer.api.ui.Alignment
import com.fs.starfarer.api.ui.TooltipMakerAPI
import com.fs.starfarer.api.util.Misc
import java.util.*



class ChronosCore : AICoreOfficerPlugin {

    var automatedPointsMult = 3f

    override fun createPerson(aiCoreId: String?, factionId: String?, random: Random?): PersonAPI? {
        var core = AICoreUtil.createCorePerson(aiCoreId, factionId)
        core.stats.level = 6
        core.setPersonality(Personalities.RECKLESS)
        core.setRankId(Ranks.SPACE_CAPTAIN)

        var portrait = "graphics/portraits/cores/rat_chronos_core.png"
        Global.getSettings().loadTexture(portrait)
        core.setPortraitSprite(portrait)

        core.stats.setSkillLevel(Skills.HELMSMANSHIP, 2F)
        core.stats.setSkillLevel(Skills.COMBAT_ENDURANCE, 2F)
        core.stats.setSkillLevel(Skills.FIELD_MODULATION, 2F)
        core.stats.setSkillLevel(Skills.TARGET_ANALYSIS, 2F)
        core.stats.setSkillLevel(Skills.GUNNERY_IMPLANTS, 2F)
        core.stats.setSkillLevel("rat_core_time", 2f)

        core.memoryWithoutUpdate.set(AICoreOfficerPlugin.AUTOMATED_POINTS_MULT, automatedPointsMult)
        return core
    }

    override fun createPersonalitySection(person: PersonAPI, tooltip: TooltipMakerAPI) {
        val opad = 10f
        val text = person!!.faction.baseUIColor
        val bg = person.faction.darkUIColor
        val spec = Global.getSettings().getCommoditySpec(person.aiCoreId)
        var desc = Global.getSettings().getDescription(spec.id, Description.Type.RESOURCE)

        var skill = Global.getSettings().getSkillSpec("rat_core_time")

        tooltip.addSpacer(10f)
        var img = tooltip.beginImageWithText(spec.iconName, 64f)
        img.addPara(desc.text1, 0f)

        img.addSpacer(5f)

        img.addPara("Automated Points Multiplier: ${(person.memoryWithoutUpdate.get(AICoreOfficerPlugin.AUTOMATED_POINTS_MULT) as Float).toInt()}x", 0f,
            Misc.getTextColor(), Misc.getHighlightColor(), "Automated Points Multiplier")

        tooltip.addImageWithText(0f)

        tooltip.addSpacer(10f)

        tooltip.addSectionHeading("Innate Skill: Deity of Time", Alignment.MID, 0f)
        tooltip.addSpacer(10f)

        var skillImg = tooltip.beginImageWithText(skill.spriteName, 48f)
        TimeCoreSkill().createCustomDescription(null, null, skillImg, tooltip.widthSoFar)

        tooltip.addImageWithText(0f)

        AICoreUtil.addPersonalityTooltip(person, tooltip)
    }

}