
package com.lilithsthrone.game.dialogue.story;

import java.io.File;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.PropertyValue;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.PlayerCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.character.body.valueEnums.BodyHair;
import com.lilithsthrone.game.character.body.valueEnums.BreastShape;
import com.lilithsthrone.game.character.body.valueEnums.CupSize;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.body.valueEnums.LabiaSize;
import com.lilithsthrone.game.character.body.valueEnums.PenisLength;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.fetishes.FetishDesire;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.markings.TattooCounterType;
import com.lilithsthrone.game.character.markings.TattooType;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.npc.dominion.Rose;
import com.lilithsthrone.game.character.npc.misc.PrologueFemale;
import com.lilithsthrone.game.character.npc.misc.PrologueMale;
import com.lilithsthrone.game.character.persona.Name;
import com.lilithsthrone.game.character.persona.NameTriplet;
import com.lilithsthrone.game.character.persona.Occupation;
import com.lilithsthrone.game.character.persona.SexualOrientation;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.character.quests.QuestType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.utils.BodyChanging;
import com.lilithsthrone.game.dialogue.utils.CharacterModificationUtils;
import com.lilithsthrone.game.dialogue.utils.CosmeticsDialogue;
import com.lilithsthrone.game.dialogue.utils.InventoryDialogue;
import com.lilithsthrone.game.dialogue.utils.InventoryInteraction;
import com.lilithsthrone.game.dialogue.utils.OptionsDialogue;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.SexAreaPenetration;
import com.lilithsthrone.game.sex.SexParticipantType;
import com.lilithsthrone.game.sex.SexType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Weather;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public class CharacterCreation {

	public static final int TIME_TO_NAME = 120;
	public static final int TIME_TO_APPEARANCE = 60;
	public static final int TIME_TO_CLOTHING = 30;
	public static final int TIME_TO_BACKGROUND = 150;
	public static final int TIME_TO_JOB = 150;
	public static final int TIME_TO_SEX_EXPERIENCE = 150;
	public static final int TIME_TO_FINAL_CHECK = 150;

	public static SpellSchool getStartingTomeSpellSchool() {
		if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 1) {
			return SpellSchool.EARTH;
		} else if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 2) {
			return SpellSchool.AIR;
		} else if(Main.game.getPlayer().getBirthMonth().getValue()  % 4 == 3) {
			return SpellSchool.WATER;
		}
		return SpellSchool.FIRE;
	}
	
	public static SpellSchool getStartingDemonstoneSpellSchool() {
		if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 2) {
			return SpellSchool.EARTH;
		} else if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 3) {
			return SpellSchool.AIR;
		} else if(Main.game.getPlayer().getBirthMonth().getValue()  % 4 == 0) {
			return SpellSchool.WATER;
		}
		return SpellSchool.FIRE;
	}

	public static final DialogueNode CHARACTER_CREATION_START = new DialogueNode("免责声明", "", true) {

		@Override
		public String getContent() {
			return Main.disclaimer;
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("同意", "您同意您已达到法定年龄可以观看色情内容，并同意接触露骨内容。", ALPHA_MESSAGE);
			} else {
				return null;
			}
		}
	};

	public static final DialogueNode ALPHA_MESSAGE = new DialogueNode("", "", true) {
		
		@Override
		public String getLabel() {
			return "版本 " + Main.VERSION_NUMBER + " | <b style='color:" + PresetColour.BASE_YELLOW_LIGHT.toWebHexString() + ";'>"+Main.VERSION_DESCRIPTION+"</b>";
		}
		
		@Override
		public String getContent() {
			return Main.getPatchNotes();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("开始", "开始创建角色。", CHOOSE_APPEARANCE){
					@Override
					public void effects() {
						Main.game.clearTextStartStringBuilder();
						Main.game.clearTextEndStringBuilder();
						Main.getProperties().setValue(PropertyValue.newWeaponDiscovered, false);
						Main.getProperties().setValue(PropertyValue.newClothingDiscovered, false);
						Main.getProperties().setValue(PropertyValue.newItemDiscovered, false);
						Main.game.getPlayer().calculateStatusEffects(0);
						Main.game.getPlayer().setHealthPercentage(1);
						Main.game.getPlayer().setManaPercentage(1);
						getDressed();
						resetBodyAppearance();
						
						Main.game.setRenderAttributesSection(true);
						Main.game.getPlayer().setName(new NameTriplet("未知", "未知", "未知"));
						Main.game.getPlayer().setSurname("");
						BodyChanging.setTarget(Main.game.getPlayer());
					}
				};
				
			} else if (index == 2) {
				return new Response("开始 (导入)", "从旧版本导入角色以开始游戏。", IMPORT_CHOOSE) {
					@Override
					public void effects() {
						Main.game.getPlayerCell().resetInventory();
					}
				};
			}
			return null;
		};
	};

	public static void resetBodyAppearance() {
		Main.game.getPlayer().setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);
		Main.game.getNpc(Lilaya.class).setSkinCovering(new Covering(BodyCoveringType.HUMAN, Main.game.getPlayer().getCovering(BodyCoveringType.HUMAN).getPrimaryColour()), true);
		Main.game.getPlayer().setSkinCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_BROWN), true);
		Main.game.getPlayer().setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BROWN), true);
		Main.game.getPlayer().setBreastShape(BreastShape.ROUND);
		Main.game.getPlayer().setVaginaLabiaSize(LabiaSize.TWO_AVERAGE.getValue());
		
		Main.game.getPlayer().setFacialHair(BodyHair.ZERO_NONE);
		resetFemininityAppearance();
	}
	
	public static void resetFemininityAppearance() {
		switch(Main.game.getPlayer().getFemininity()) {
			case MASCULINE_STRONG:
				Main.game.getPlayer().setUnderarmHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setAssHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setPubicHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setPenisSize(PenisLength.TWO_AVERAGE.getMedianValue()+3);
				break;
			case MASCULINE:
				Main.game.getPlayer().setUnderarmHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setAssHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setPubicHair(BodyHair.FOUR_NATURAL);
				Main.game.getPlayer().setPenisSize(PenisLength.TWO_AVERAGE);
				break;
			case ANDROGYNOUS:
				Main.game.getPlayer().setUnderarmHair(BodyHair.ZERO_NONE);
				Main.game.getPlayer().setAssHair(BodyHair.TWO_MANICURED);
				Main.game.getPlayer().setPubicHair(BodyHair.FOUR_NATURAL);
				if(Main.game.getPlayer().hasPenis()) {
					Main.game.getPlayer().setPenisSize(PenisLength.ONE_TINY);
				}
				if(Main.game.getPlayer().hasVagina()) {
					Main.game.getPlayer().setBreastSize(CupSize.A);
				}
				break;
			case FEMININE:
				Main.game.getPlayer().setUnderarmHair(BodyHair.ZERO_NONE);
				Main.game.getPlayer().setAssHair(BodyHair.TWO_MANICURED);
				Main.game.getPlayer().setPubicHair(BodyHair.THREE_TRIMMED);
				Main.game.getPlayer().setBreastSize(CupSize.C);
				break;
			case FEMININE_STRONG:
				Main.game.getPlayer().setUnderarmHair(BodyHair.ZERO_NONE);
				Main.game.getPlayer().setAssHair(BodyHair.ZERO_NONE);
				Main.game.getPlayer().setPubicHair(BodyHair.ZERO_NONE);
				Main.game.getPlayer().setBreastSize(CupSize.DD);
				break;
		}
	}
	
	public static void setGenderFemale() {
		Femininity fem = Femininity.FEMININE;
		switch(BodyChanging.getTarget().getFemininity()) {
			case ANDROGYNOUS:
				fem = Femininity.ANDROGYNOUS;
				break;
			case MASCULINE:
				fem = Femininity.FEMININE;
				break;
			case MASCULINE_STRONG:
				fem = Femininity.FEMININE_STRONG;
				break;
			default:
				break;
		}
		BodyChanging.getTarget().setBody(Gender.F_V_B_FEMALE, RacialBody.HUMAN, RaceStage.HUMAN, false);
		BodyChanging.getTarget().setFemininity(fem.getMedianFemininity());
		
		if(BodyChanging.getTarget().isPlayer()) {
			getDressed();
			CharacterCreation.resetBodyAppearance();
		}
	}
	
	public static void setGenderMale() {
		Femininity fem = Femininity.MASCULINE;
		switch(BodyChanging.getTarget().getFemininity()) {
			case ANDROGYNOUS:
				fem = Femininity.ANDROGYNOUS;
				break;
			case FEMININE:
				fem = Femininity.MASCULINE;
				break;
			case FEMININE_STRONG:
				fem = Femininity.MASCULINE_STRONG;
				break;
			default:
				break;
		}
		BodyChanging.getTarget().setBody(Gender.M_P_MALE, RacialBody.HUMAN, RaceStage.HUMAN, false);
		BodyChanging.getTarget().setFemininity(fem.getMedianFemininity());

		if(BodyChanging.getTarget().isPlayer()) {
			getDressed();
			CharacterCreation.resetBodyAppearance();
		}
	}
	
	private static void equipPiercings() {
		Colour colour1 = PresetColour.CLOTHING_BLACK_STEEL;
		Colour colour2 = PresetColour.CLOTHING_STEEL;

		if(Main.game.getPlayer().getFemininity()==Femininity.FEMININE_STRONG) {
			colour1 = PresetColour.CLOTHING_PLATINUM;
			colour2 = PresetColour.CLOTHING_GOLD;
		} else if(Main.game.getPlayer().isFeminine()) {
			colour1 = PresetColour.CLOTHING_SILVER;
			colour2 = PresetColour.CLOTHING_SILVER;
		}
		
		Map<InventorySlot, AbstractClothing> pendingPiercings = new HashMap<>();
		
		// Ear piercings:
		if(Main.game.getPlayer().isPiercedEar()) {
			if(Main.game.getPlayer().getFemininity()==Femininity.FEMININE_STRONG) {
				pendingPiercings.put(InventorySlot.PIERCING_EAR, Main.game.getItemGen().generateClothing("innoxia_piercing_ear_chain_dangle", colour1, false));
			} else if(Main.game.getPlayer().getFemininity()==Femininity.FEMININE) {
				pendingPiercings.put(InventorySlot.PIERCING_EAR, Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ring", colour1, false));
			} else {
				pendingPiercings.put(InventorySlot.PIERCING_EAR, Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ball_studs", colour1, false));
			}
		}
		
		// Lip piercings:
		if(Main.game.getPlayer().isPiercedLip()) {
			pendingPiercings.put(InventorySlot.PIERCING_LIP, Main.game.getItemGen().generateClothing("innoxia_piercing_lip_double_ring", colour1, false));
		}
		
		// Navel piercings:
		if(Main.game.getPlayer().isPiercedNavel()) {
			if(Main.game.getPlayer().isFeminine()) {
				pendingPiercings.put(InventorySlot.PIERCING_STOMACH, Main.game.getItemGen().generateClothing("innoxia_piercing_gemstone_barbell", colour2, false));
			} else {
				pendingPiercings.put(InventorySlot.PIERCING_STOMACH, Main.game.getItemGen().generateClothing("innoxia_piercing_ringed_barbell", colour2, false));
			}
		}

		// Nipples piercings:
		if(Main.game.getPlayer().isPiercedNipple()) {
			pendingPiercings.put(InventorySlot.PIERCING_NIPPLE, Main.game.getItemGen().generateClothing("innoxia_piercing_basic_barbell_pair", colour2, false));
		}

		// Nose piercings:
		if(Main.game.getPlayer().isPiercedNose()) {
			if(Main.game.getPlayer().isFeminine()) {
				pendingPiercings.put(InventorySlot.PIERCING_NOSE, Main.game.getItemGen().generateClothing("innoxia_piercing_nose_ring", colour1, false));
			} else {
				pendingPiercings.put(InventorySlot.PIERCING_NOSE, Main.game.getItemGen().generateClothing("innoxia_piercing_nose_ball_stud", colour1, false));
			}
		}

		// Penis piercings:
		if(Main.game.getPlayer().hasPenis() && Main.game.getPlayer().isPiercedPenis()) {
			pendingPiercings.put(InventorySlot.PIERCING_PENIS, Main.game.getItemGen().generateClothing("innoxia_piercing_penis_ring", colour2, false));
		}

		// Tongue piercings:
		if(Main.game.getPlayer().isPiercedTongue()) {
			pendingPiercings.put(InventorySlot.PIERCING_TONGUE, Main.game.getItemGen().generateClothing("innoxia_piercing_basic_barbell", colour1, false));
		}

		// Vagina piercings:
		if(Main.game.getPlayer().hasVagina() && Main.game.getPlayer().isPiercedVagina()) {
			pendingPiercings.put(InventorySlot.PIERCING_VAGINA, Main.game.getItemGen().generateClothing("innoxia_piercing_ringed_barbell", colour2, false));
		}
		
		for(InventorySlot slot : InventorySlot.getPiercingSlots()) {
			AbstractClothing clothingCurrentlyInSlot = Main.game.getPlayer().getClothingInSlot(slot);
			
			if(pendingPiercings.get(slot)!=null){
				if(clothingCurrentlyInSlot==null || clothingCurrentlyInSlot.getClothingType()!=pendingPiercings.get(slot).getClothingType()) {
					Main.game.getPlayer().equipClothingFromNowhere(pendingPiercings.get(slot), slot, true, Main.game.getPlayer());
				}
				
			} else if(clothingCurrentlyInSlot!=null){
				Main.game.getPlayer().unequipClothingIntoVoid(slot, true, Main.game.getPlayer());
			}
		}
	}
	
	public static void getDressed() {
		getDressed(Main.game.getPlayer(), true);
	}
	
	public static void getDressed(GameCharacter character, boolean spawnClothingOnFloor) {
		character.resetInventory(false);
		Main.game.getPlayerCell().resetInventory();
		
		equipPiercings();
		
		switch(character.getFemininity()) {
			case MASCULINE_STRONG:
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_briefs", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_long_sleeved_shirt", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_neck_tie", PresetColour.CLOTHING_RED, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torsoOver_suit_jacket", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_trousers", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_mens_smart_shoes", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_ring", PresetColour.CLOTHING_GOLD, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_GOLD, false), true, character);
				
				if(spawnClothingOnFloor) {
					spawnClothingInArea();
				}
				break;
				
			case MASCULINE:
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_trousers", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_mens_smart_shoes", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_ring", PresetColour.CLOTHING_SILVER, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_SILVER, false), true, character);

				if(spawnClothingOnFloor) {
					spawnClothingInArea();
				}
				break;
				
			case ANDROGYNOUS:
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_panties", PresetColour.CLOTHING_WHITE, false), true, character);
				if(character.getBreastRawSizeValue()!=0) {
					character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_croptop_bra", PresetColour.CLOTHING_WHITE, false), true, character);
				} else {
					Main.game.getPlayerCell().getInventory().addClothing(Main.game.getItemGen().generateClothing("innoxia_chest_croptop_bra", PresetColour.CLOTHING_WHITE, false));
				}
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_GREY, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_low_top_skater_shoes", PresetColour.CLOTHING_RED, false), true, character);
				
				if(spawnClothingOnFloor) {
					spawnClothingInArea();
				}
				break;
				
			case FEMININE:
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_panties", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_plunge_bra", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_skater_dress", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_trainer_socks", PresetColour.CLOTHING_WHITE, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_heels", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_WOMENS_WATCH, PresetColour.CLOTHING_PINK_LIGHT, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_ring", PresetColour.CLOTHING_SILVER, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_neck_heart_necklace", PresetColour.CLOTHING_SILVER, false), true, character);

				if(spawnClothingOnFloor) {
					spawnClothingInArea();
				}
				break;
				
			case FEMININE_STRONG:
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_thong", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_plunge_bra", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_slip_dress", PresetColour.CLOTHING_RED_BURGUNDY, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_pantyhose", PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_stiletto_heels", PresetColour.CLOTHING_RED_BURGUNDY, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_WOMENS_WATCH, PresetColour.CLOTHING_BLACK, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_ring", PresetColour.CLOTHING_GOLD, false), true, character);
				character.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_neck_heart_necklace", PresetColour.CLOTHING_GOLD, false), true, character);

				if(spawnClothingOnFloor) {
					spawnClothingInArea();
				}
				break;
			default:
				break;
		}
		
		if(character.isPlayer()
				&& ((character.getName(false).equals("James") || character.getName(false).equals("Jane") || character.getName(false).equals("Tracy")) && character.getSurname().equals("Bond"))) {
			character.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon(WeaponType.getWeaponTypeFromId("innoxia_western_kkp_western_kkp")));
		}
	}
	
	private static void generateClothingOnFloor(String clothingType, Colour colour) {
		generateClothingOnFloor(ClothingType.getClothingTypeFromId(clothingType), colour, null, null);
	}
	
	private static void generateClothingOnFloor(AbstractClothingType clothingType, Colour colour) {
		generateClothingOnFloor(clothingType, colour, null, null);
	}
	
	private static void generateClothingOnFloor(AbstractClothingType clothingType, Colour colour, Colour colour2, Colour colour3) {
		for(AbstractClothing clothing : Main.game.getPlayer().getClothingCurrentlyEquipped()) {
			if(clothing.getClothingType()==clothingType) {
				return;
			}
		}
		Main.game.getPlayerCell().getInventory().addClothing(Main.game.getItemGen().generateClothing(clothingType, colour, colour2, colour3, false));
	}
	
	private static void spawnClothingInArea() {
		switch(Main.game.getPlayer().getFemininity()) {
			case MASCULINE:
			case MASCULINE_STRONG:
				generateClothingOnFloor("bloom_wasp609_rainCoat_rain_coat", PresetColour.CLOTHING_BLUE_NAVY);
				generateClothingOnFloor(ClothingType.getClothingTypeFromId("innoxia_foot_trainers"), PresetColour.CLOTHING_WHITE, PresetColour.CLOTHING_BLUE_GREY, PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_foot_work_boots", PresetColour.CLOTHING_TAN);
				generateClothingOnFloor("innoxia_foot_low_top_skater_shoes", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_sock_socks", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_leg_cargo_trousers", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_GREY);
				generateClothingOnFloor("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_eye_aviators", PresetColour.CLOTHING_BLACK_STEEL);
				generateClothingOnFloor("innoxia_eye_glasses", PresetColour.CLOTHING_BLACK_STEEL);
				generateClothingOnFloor("innoxia_hand_gloves", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_head_cap", PresetColour.CLOTHING_BLUE);
				generateClothingOnFloor("innoxia_neck_scarf", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_torsoOver_hoodie", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_torsoOver_ribbed_jumper", PresetColour.CLOTHING_GREY);


				generateClothingOnFloor("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_torso_tshirt", PresetColour.CLOTHING_BLUE_LIGHT);
				generateClothingOnFloor("innoxia_groin_briefs", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_torso_long_sleeved_shirt", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_neck_tie", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_torsoOver_suit_jacket", PresetColour.CLOTHING_BLACK);
				break;
				
			case ANDROGYNOUS:
				generateClothingOnFloor("bloom_wasp609_rainCoat_rain_coat", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor(ClothingType.getClothingTypeFromId("innoxia_foot_trainers"), PresetColour.CLOTHING_WHITE, PresetColour.CLOTHING_PURPLE_DARK, PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_foot_heels", PresetColour.CLOTHING_BLACK);
				
				generateClothingOnFloor("innoxia_groin_thong", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_groin_lacy_panties", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_groin_briefs", PresetColour.CLOTHING_WHITE);

				generateClothingOnFloor("innoxia_chest_lacy_plunge_bra", PresetColour.CLOTHING_RED);

				generateClothingOnFloor("innoxia_sock_kneehigh_socks", PresetColour.CLOTHING_WHITE);

				generateClothingOnFloor("innoxia_leg_cargo_trousers", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_trousers", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_skirt", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_yoga_pants", PresetColour.CLOTHING_PINK_LIGHT);
				generateClothingOnFloor("innoxia_leg_tight_jeans", PresetColour.CLOTHING_BLUE_NAVY);
				generateClothingOnFloor("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_GREY);
				generateClothingOnFloor("innoxia_leg_distressed_jeans", PresetColour.CLOTHING_BLUE_GREY);

				generateClothingOnFloor("innoxia_neck_scarf", PresetColour.CLOTHING_RED);
				
				generateClothingOnFloor("innoxia_head_cap", PresetColour.CLOTHING_BLUE);
				
				generateClothingOnFloor("innoxia_stomach_underbust_corset", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor("innoxia_torso_tshirt", PresetColour.CLOTHING_BLUE_LIGHT);
				generateClothingOnFloor("innoxia_torso_blouse", PresetColour.CLOTHING_BLUE_LIGHT);
				generateClothingOnFloor("innoxia_torso_cami_straps", PresetColour.CLOTHING_GREEN);
				
				generateClothingOnFloor("innoxia_torsoOver_hoodie", PresetColour.CLOTHING_PINK_LIGHT);
				generateClothingOnFloor("innoxia_torsoOver_open_front_cardigan", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor("innoxia_finger_ring", PresetColour.CLOTHING_SILVER);
				generateClothingOnFloor("innoxia_neck_heart_necklace", PresetColour.CLOTHING_SILVER);
				generateClothingOnFloor("innoxia_wrist_bangle", PresetColour.CLOTHING_SILVER);
				generateClothingOnFloor("innoxia_ankle_anklet", PresetColour.CLOTHING_SILVER);
				
				generateClothingOnFloor("innoxia_eye_glasses", PresetColour.CLOTHING_BLACK_STEEL);
				break;
				
			case FEMININE:
			case FEMININE_STRONG:
				generateClothingOnFloor("bloom_wasp609_rainCoat_rain_coat", PresetColour.CLOTHING_PURPLE_DARK);
				generateClothingOnFloor("innoxia_torsoOver_womens_winter_coat", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_sock_stockings", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor(ClothingType.HIPS_SUSPENDER_BELT, PresetColour.CLOTHING_BLACK);
				
				generateClothingOnFloor("innoxia_groin_panties", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_groin_thong", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_groin_lacy_panties", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_groin_vstring", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor("innoxia_chest_lacy_plunge_bra", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_chest_fullcup_bra", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor("innoxia_sock_pantyhose", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_sock_kneehigh_socks", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_sock_thighhigh_socks", PresetColour.CLOTHING_WHITE);

				generateClothingOnFloor("innoxia_eye_aviators", PresetColour.CLOTHING_ROSE_GOLD);
				generateClothingOnFloor("innoxia_eye_glasses", PresetColour.CLOTHING_BLACK_STEEL);

				generateClothingOnFloor("innoxia_foot_ankle_boots", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_foot_low_top_skater_shoes", PresetColour.CLOTHING_PINK_LIGHT);
				generateClothingOnFloor("innoxia_foot_thigh_high_boots", PresetColour.CLOTHING_TAN);
				generateClothingOnFloor("innoxia_foot_stiletto_heels", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_foot_heels", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor("innoxia_hand_elbow_length_gloves", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_head_headband", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor(ClothingType.getClothingTypeFromId("innoxia_head_headband_bow"), PresetColour.CLOTHING_PINK_LIGHT, PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_PINK);

				generateClothingOnFloor("innoxia_leg_hotpants", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_leg_mini_skirt", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_skirt", PresetColour.CLOTHING_PINK);
				generateClothingOnFloor("innoxia_leg_yoga_pants", PresetColour.CLOTHING_PINK_LIGHT);
				generateClothingOnFloor("innoxia_leg_pencil_skirt", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_leg_tight_jeans", PresetColour.CLOTHING_BLUE_NAVY);
				generateClothingOnFloor("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_GREY);
				generateClothingOnFloor("innoxia_leg_distressed_jeans", PresetColour.CLOTHING_BLUE_GREY);
				
				generateClothingOnFloor("innoxia_neck_scarf", PresetColour.CLOTHING_RED);
				
				generateClothingOnFloor("innoxia_stomach_underbust_corset", PresetColour.CLOTHING_BLACK);

				generateClothingOnFloor(ClothingType.getClothingTypeFromId("innoxia_torso_feminine_short_sleeve_shirt"), PresetColour.CLOTHING_BLUE_LIGHT);
				generateClothingOnFloor("innoxia_torso_blouse", PresetColour.CLOTHING_BLUE_LIGHT);
				generateClothingOnFloor("innoxia_torso_cami_straps", PresetColour.CLOTHING_GREEN);
				generateClothingOnFloor("innoxia_torso_long_sleeve_dress", PresetColour.CLOTHING_BLACK);
				generateClothingOnFloor("innoxia_torso_short_croptop", PresetColour.CLOTHING_PINK);
				generateClothingOnFloor("innoxia_torso_virgin_killer_sweater", PresetColour.CLOTHING_WHITE);
				generateClothingOnFloor("innoxia_torso_slip_dress", PresetColour.CLOTHING_RED);
				generateClothingOnFloor("innoxia_torso_skater_dress", PresetColour.CLOTHING_BLACK);
				
				generateClothingOnFloor("innoxia_torsoOver_open_front_cardigan", PresetColour.CLOTHING_BLACK);
				
				generateClothingOnFloor("innoxia_wrist_bangle", PresetColour.CLOTHING_GOLD);
				generateClothingOnFloor("innoxia_ankle_anklet", PresetColour.CLOTHING_GOLD);
				break;
		}
	}
	
	public static final DialogueNode CHOOSE_APPEARANCE = new DialogueNode("夜晚外出", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<p>"
						+ "当出租车终于停在大英博物馆门口时，你已经迟到了近五分钟。"
						+ " 你这次来伦敦的全部原因，就是为了参加你莉莉阿姨新展览的开幕晚会，"
							+ " 当你匆忙付了车费下车时，你希望她还没有开始演讲。"
					+ "</p>"
					+ "<p>"
						+ "你冲向入口时，街灯闪烁着亮了起来，用暗淡的橙色光芒照亮了你的周围。"
						+ " 没过多久，你就站在了博物馆的正门前，让你懊恼的是，你看到门前已经排起了一个小队。"
						+ " 别无选择，你只好排队等候，你瞥了一眼大楼现代风格外墙上的巨大玻璃窗，看到了自己模糊的倒影……"
					+ "</p>"
					+ "<br/>"
					
					+ CharacterModificationUtils.getStartDateDiv()
					
					+ "<div class='cosmetics-container' style='background:transparent;'>"
					
						+ CharacterModificationUtils.getGenderChoiceDiv()
						
						+ CharacterModificationUtils.getFemininityChoiceDiv()
						
						+ "<div class='container-full-width' style='text-align:center;'>"
							+ "你将被称呼为<span style='color:"+Main.game.getPlayer().getGender().getColour().toWebHexString()+";'>"
								+UtilText.generateSingularDeterminer(Main.game.getPlayer().getGender().getName())+ " " + Main.game.getPlayer().getGender().getName()+"</span>。<br/>"
							+ "<i>你可以在选项菜单中更改所有性别名称。</i>"
						+ "</div>"

						+ CharacterModificationUtils.getBirthdayChoiceDiv()
						
						+ CharacterModificationUtils.getOrientationChoiceDiv()
						
						+ CharacterModificationUtils.getPersonalityChoiceDiv(false)
						
					+"</div>";
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("继续", "排队等候，希望活动还没有开始。", CHOOSE_NAME) {
					@Override
					public int getSecondsPassed() {
						return TIME_TO_NAME;
					}
					@Override
					public void effects() {
//						getDressed();
					}
				};
			}
//			else if (index == 0) {
//				return new Response("返回", "返回主菜单。", OptionsDialogue.MENU);
//			}
			return null;
		}
	};
	
	public static final DialogueNode CHOOSE_NAME = new DialogueNode("夜晚外出", "", true) {

		boolean unsuitableName = false, unsuitableSurname = false;
		
		@Override
		public String getHeaderContent() {
			return "<p>"
						+ "[npcMale.speech("+(Main.game.getPlayer().isFeminine()?"小姐":"先生")+",)]"
						+ " 门卫向你喊道，显然他已经接待完了排在你前面的人，"
						+ " [npcMale.speech(请问您有邀请函吗？)]"
					+ "</p>"
					+ "<p>"
						+ "你从玻璃窗前转过身，微笑着向前走去。"
						+ " [pc.speech(是的，就在这里……呃……等一下……)]"
					+ "</p>"
					+ "<p>"
						+ "你把手伸进你的"+(Main.game.getPlayer().isFeminine()?"手提包":"口袋")+"，发现邀请函并不在里面，你的心开始狂跳起来。"
						+ " [pc.speech(不，不，不！我一定是忘在出租车上了！)]"
					+ "</p>"
					+ "<p>"
						+ "[npcMale.speech(哦，别担心，)]"
						+ " 那人回答说，"
						+ " [npcMale.speech(如果你能告诉我你的名字，我可以核对一下名单。)]"
					+ "</p>"
					+ "<p>"
						+ "你松了一口气，告诉了那人你的名字……"
					+ "</p>"
					+"<br/>"
					+ "<div class='container-full-width' style='text-align:center;'>"
						+ "<div style='position:relative; display:inline-block; padding-bottom:0; margin 0 auto; vertical-align:middle; width:100%; text-align:center;'>"
							+ "<i>"
								+ "你的名字可以设定三个：男性化名字、中性化名字和女性化名字。"
								+ " 你的名字会自动切换到与你身体女性化程度相对应的那个。"
							+ "</i>"
							+ "<br/>"
							+ "<p style='display:inline-block; padding:0; margin:0; height:32px; line-height:32px; width:100px;'>名字： </p>"
							+ "</form style='display:inline-block; padding:0; margin:0; text-align:center;'>"
									+ "<input type='text' id='nameMasculineInput' style=' color:"+PresetColour.MASCULINE.toWebHexString()+";' value='"+ UtilText.parseForHTMLDisplay(Main.game.getPlayer().getNameTriplet().getMasculine())+ "'>"
									
							+ "</form style='display:inline-block; padding:0; margin:0; text-align:center;'>"
								+ "<input type='text' id='nameAndrogynousInput' style=' color:"+PresetColour.ANDROGYNOUS.toWebHexString()+";' value='"+ UtilText.parseForHTMLDisplay(Main.game.getPlayer().getNameTriplet().getAndrogynous())+ "'>"
								
							+ "</form style='display:inline-block; padding:0; margin:0; text-align:center;'>"
								+ "<input type='text' id='nameFeminineInput' style=' color:"+PresetColour.FEMININE.toWebHexString()+";' value='"+ UtilText.parseForHTMLDisplay(Main.game.getPlayer().getNameTriplet().getFeminine())+ "'>"
							
							+ "<br/>"
							+ "<p style='display:inline-block; padding:0; margin:0; height:32px; line-height:32px; width:100px;'>姓氏： </p>"
							+ "<form style='display:inline-block; padding:0; margin:0; text-align:center;'><input type='text' id='surnameInput' value='"+ UtilText.parseForHTMLDisplay(Main.game.getPlayer().getSurname())+ "'></form>"
						+ "</div>"
						+ "<br/>"
						+ "<i>你的名字长度必须在2到32个字符之间。不能使用方括号或句号。（姓氏可以留空。）</i>"
						+ (unsuitableName ? "<p style='text-align:center;padding-top:0;'><b style=' color:"+ PresetColour.GENERIC_BAD.toWebHexString()+ ";'>无效的名字。</b></p>" : "")
						+ (unsuitableSurname ? "<p style='text-align:center;padding-top:0;'><b style=' color:"+ PresetColour.GENERIC_BAD.toWebHexString()+ ";'>无效的姓氏。</b></p>" : "")
					+ "</div>"
					
					+ "<p id='hiddenFieldName' style='display:none;'></p>"
					+ "<p id='hiddenFieldSurname' style='display:none;'></p>";
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseEffectsOnly("继续", "使用这个名字并继续到角色创建的下一个阶段。"){
					@Override
					public int getSecondsPassed() {
						if (unsuitableName || unsuitableSurname)  {
							return super.getSecondsPassed();
						}
						return TIME_TO_APPEARANCE;
					}
					@Override
					public void effects() {
						List<String> fieldsList = Util.newArrayListOfValues("nameMasculineInput", "nameAndrogynousInput", "nameFeminineInput");
						List<String> namesList = new ArrayList<>();
						for(String s : fieldsList) {
							Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenFieldName').innerHTML=document.getElementById('"+s+"').value;");
							if(Main.mainController.getWebEngine().getDocument()!=null) {
								if (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().length() < 2
										|| Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().length() > 32
										|| !Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().matches("[^\\[\\]\\.]+")) {
									unsuitableName = true;
								} else {
									unsuitableName = false;
									namesList.add(Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent());
								}
							}
						}
						Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenFieldSurname').innerHTML=document.getElementById('surnameInput').value;");
						if(Main.mainController.getWebEngine().getDocument()!=null) {
							if (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().length()>=1
									&& (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().length() > 32
											|| !Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().matches("[^\\[\\]\\.]+"))) {
								unsuitableSurname = true;
							} else {
								unsuitableSurname = false;
							}
						}
						if (unsuitableName || unsuitableSurname)  {
							Main.game.setContent(new Response("" ,"", CHOOSE_NAME));
							
						} else {
							Main.game.getPlayer().setName(new NameTriplet(namesList.get(0), namesList.get(1), namesList.get(2)));
							Main.game.getPlayer().setSurname(Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent());

							Main.game.getPlayerCell().resetInventory();
							Main.game.getPlayer().moveToAdjacentMatchingCellType(false, PlaceType.MUSEUM_LOBBY);
							Main.game.setContent(new Response("" ,"", CHOOSE_ADVANCED_APPEARANCE));
							getDressed();
						}
					}
				};
				
			} else if (index == 2) {
				return new Response("随机", "根据你的性别生成一个随机名字。", CHOOSE_NAME){
					@Override
					public void effects() {
						Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenFieldSurname').innerHTML=document.getElementById('surnameInput').value;");
						if(Main.mainController.getWebEngine().getDocument()!=null) {
							if (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().length()>=1
									&& (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().length() > 16
											|| !Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent().matches("[^\\[\\]\\.]+")))
								unsuitableSurname = true;
							else {
								unsuitableSurname = false;
							}
						}
						if(!unsuitableSurname) {
							Main.game.getPlayer().setSurname(Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldSurname").getTextContent());
						}

						Main.game.getPlayer().setName(Name.getRandomTriplet(Subspecies.HUMAN));
					}
				};
				
			} else if (index == 3) {
				return new Response("随机姓氏", "生成一个随机姓氏。", CHOOSE_NAME){
					@Override
					public void effects() {
						List<String> fieldsList = Util.newArrayListOfValues("nameMasculineInput", "nameAndrogynousInput", "nameFeminineInput");
						List<String> namesList = new ArrayList<>();
						for(String s : fieldsList) {
							Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenFieldName').innerHTML=document.getElementById('"+s+"').value;");
							if(Main.mainController.getWebEngine().getDocument()!=null) {
								if (Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().length() < 2
										|| Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().length() > 16
										|| !Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent().matches("[^\\[\\]\\.]+"))
									unsuitableName = true;
								else {
									unsuitableName = false;
									namesList.add(Main.mainController.getWebEngine().getDocument().getElementById("hiddenFieldName").getTextContent());
								}
							}
						}
						if(!unsuitableName) {
							Main.game.getPlayer().setName(new NameTriplet(namesList.get(0), namesList.get(1), namesList.get(2)));
						}
						
						Main.game.getPlayer().setSurname(Name.getSurname(Main.game.getPlayer()));
					}
				};
				
			} else if (index == 0) {
				return new Response("返回", "返回性别选择。", CHOOSE_APPEARANCE) {
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_NAME;
					}
				};
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE = new DialogueNode("在博物馆里", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<p>"
						+ "[pc.speech(是"+(Main.game.getPlayer().getSurname().length()!=0?"[pc.surname]，[pc.name] [pc.surname]":"[pc.name]")+",)]"
						+ " 你说道，不耐烦地低头看着那人扫描名单的剪贴板。"
					+ "</p>"
					+ "<p>"
						+ "终于，你看到他的手指划过了你的名字，他微笑着往旁边一站，示意你前进。"
						+ " [npcMale.speech(祝您有个愉快的夜晚，"+(Main.game.getPlayer().getSurname().length()!=0
								?(Main.game.getPlayer().isFeminine()?"[pc.surname]小姐":"[pc.surname]先生")
								:(Main.game.getPlayer().isFeminine()?"小姐":"先生"))+"。)]"
					+ "</p>"
					+ "<p>"
						+ "你向他道了谢，匆匆穿过入口，片刻之后，你发现自己已经踏入了博物馆巨大的中央大厅。"
						+ " 楼上阳台上挂着巨大的横幅，上面用粗体字自豪地宣告着‘阿卡德帝国展览：开幕之夜’。"
						+ " 在大厅的另一边，你看到成群的人围着一个大舞台，当你注意到舞台上目前空无一人时，你松了一口气。"
					+ "</p>"
					+ "<p>"
						+ "[pc.thought(呼……总算及时赶到了……)]"
					+ "</p>"
					+ "<p>"
						+ "看来莉莉的开幕演讲和你一样迟到了，你决定走到附近的一面镜子前，确保自己的仪容得体……"
					+ "</p>"
					+ "<br/>"
					+ "<div class='container-full-width'>"
						+ "<h5 style='text-align:center;'>外貌</h5>"
						+ Main.game.getPlayer().getBodyDescription()
					+ "</div>"
					+ "<br/>"
					+ "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>你可以通过进入下面的各个子菜单来修改你的外貌。</i>"
					+ "</div>";
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("继续",
						"赶到这里后你的衣服有点乱。在前往主舞台前先整理一下自己。",
						InventoryDialogue.INVENTORY_MENU) {
					@Override
					public int getSecondsPassed() {
						return TIME_TO_CLOTHING;
					}
					@Override
					public void effects() {
						equipPiercings();
						InventoryDialogue.setBuyback(false);
						InventoryDialogue.setInventoryNPC(null);
						InventoryDialogue.setNPCInventoryInteraction(InventoryInteraction.CHARACTER_CREATION);
					}
				};
				
			} else if (index == 2) {
				return new Response("核心", "进入身体所有核心方面的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_CORE);
				
			} else if (index == 3) {
				return new Response("面部", "进入面部相关方面的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_FACE);
				
			} else if (index == 4) {
				return new Response("头发", "进入头发的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_HAIR);
				
			} else if (index == 5) {
				return new Response("乳房", "进入乳房的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_BREASTS);
				
			} else if (index == 6) {
				return new Response("臀部与胯部", "进入臀部、胯部和肛门相关方面的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_ASS);
				
			} else if (index == 7) {
				return new Response((Main.game.getPlayer().hasPenis()?"阴茎":"阴道"), "进入与你的"+(Main.game.getPlayer().hasPenis()?"阴茎":"阴道")+"相关方面的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_GENITALS);
				
			}  else if (index == 8) {
				return new Response("化妆", "进入化妆的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_COSMETICS);
				
			} else if (index == 9) {
				return new Response("穿环", "进入身体穿环的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_PIERCINGS);
				
			} else if (index == 10) {
				return new Response("纹身", "进入纹身的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_TATTOOS);
				
			} else if (index == 11) {
				return new Response("额外毛发", "进入面部、阴部和身体毛发的自定义菜单。", CHOOSE_ADVANCED_APPEARANCE_BODY_HAIR);
				
			} else if (index == 0) {
				return new Response("返回", "返回姓名选择界面。", CHOOSE_NAME) {
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_APPEARANCE;
					}
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(WorldType.MUSEUM, PlaceType.MUSEUM_ENTRANCE, false);
					}
				};
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_CORE = new DialogueNode("核心身体外观", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项在游戏后期都可以被影响。</i>"
					+ "</div>"
						
					+ CharacterModificationUtils.getHeightChoiceDiv(true)
					
					+ CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.HUMAN, BodyCoveringType.HUMAN, "肤色", "覆盖你身体的皮肤颜色。", true, false, false)
					


					+ "<div class='cosmetics-container' style='background:transparent;'>"
					
						+ CharacterModificationUtils.getBodySizeChoiceDiv()
						
						+ CharacterModificationUtils.getMuscleChoiceDiv()
						
						+ "<div class='container-full-width' style='text-align:center;'>"
							+ "你的肌肉和体型值使你的外表呈现为：<br/>"
							+ "<b style='color:"+Main.game.getPlayer().getBodyShape().toWebHexStringColour()+";'>"+Util.capitaliseSentence(Main.game.getPlayer().getBodyShape().getName(false))+"</b>"
						+ "</div>"
					
					+"</div>";
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_FACE = new DialogueNode("面部外观", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"

					+ CharacterModificationUtils.getLipSizeDiv()
					
					+ CharacterModificationUtils.getLipPuffynessDiv()

					+ CharacterModificationUtils.getKatesDivCoveringsNew(false, Main.game.getPlayer().getEyeType().getRace(), BodyCoveringType.EYE_HUMAN, "虹膜颜色", "你眼睛虹膜的颜色。", true, false, false);
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_HAIR = new DialogueNode("头发外观", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"

					+ CharacterModificationUtils.getKatesDivHairLengths(false, "头发长度", "选择你的头发有多长。")
					
					+ CharacterModificationUtils.getKatesDivHairStyles(false, "发型", "选择你的发型。某些发型在头发较短时不可用。")

					+ CharacterModificationUtils.getKatesDivCoveringsNew(false, Main.game.getPlayer().getHairType().getRace(), BodyCoveringType.HAIR_HUMAN, "发色", "你头发的颜色。", true, false);
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_BREASTS = new DialogueNode("乳房外观", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"
						
					+ CharacterModificationUtils.getBreastSizeDiv()
					
					+ CharacterModificationUtils.getBreastShapeDiv()
					
					+ CharacterModificationUtils.getNippleSizeDiv()
					
					+ CharacterModificationUtils.getAreolaeSizeDiv()
					
					+ CharacterModificationUtils.getNipplePuffynessDiv()
					
					+ CharacterModificationUtils.getSelfTransformLactationDiv();
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_ASS = new DialogueNode("臀部外观", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"
						
					+ CharacterModificationUtils.getAssSizeDiv()
					
					+ CharacterModificationUtils.getHipSizeDiv()
					
					+ CharacterModificationUtils.getBleachedAnusDiv();
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_GENITALS = new DialogueNode("生殖器外观", "", true) {
		
		@Override
		public String getLabel() {
			if(Main.game.getPlayer().hasPenis()) {
				return "阴茎外观";
			} else {
				return "阴道外观";
			}
		}
		
		@Override
		public String getHeaderContent() {
			if(Main.game.getPlayer().hasPenis()) {
				return "<div class='container-full-width' style='text-align:center;'>"
							+ "<i>所有这些选项都可以在游戏后期改变。</i>"
						+ "</div>"
							
							+ CharacterModificationUtils.getPenisSizeDiv()
							
							+ CharacterModificationUtils.getTesticleSizeDiv()
							
							+ CharacterModificationUtils.getSelfTransformCumProductionDiv();
				
			} else {
				return "<div class='container-full-width' style='text-align:center;'>"
							+ "<i>所有这些选项都可以在游戏后期改变。</i>"
						+ "</div>"
	
							+ CharacterModificationUtils.getVaginaCapacityDiv()
							
							+ CharacterModificationUtils.getLabiaSizeDiv()
							
							+ CharacterModificationUtils.getClitorisSizeDiv();
				
			}
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_PIERCINGS = new DialogueNode("穿环", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"
						
					+CharacterModificationUtils.getKatesDivPiercings(true);
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_TATTOOS = new DialogueNode("纹身", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>在游戏后期，你可以获得附魔和发光的纹身。但目前，你的纹身选择仅限于更普通的选项。</i>"
					+ "</div>"
					+CharacterModificationUtils.getKatesDivTattoos();
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_TATTOOS_ADD = new DialogueNode("魅魔的秘密", "-", true) {

		@Override
		public String getLabel() {
			return "添加纹身： "+Util.capitaliseSentence(CharacterModificationUtils.tattooInventorySlot.getTattooSlotName());
		}
		
		@Override
		public String getContent() {
			return CharacterModificationUtils.getKatesDivTattoosAdd();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if(CharacterModificationUtils.tattoo.getType().equals(TattooType.getTattooTypeFromId("innoxia_misc_none"))
						&& CharacterModificationUtils.tattoo.getWriting().getText().isEmpty()
						&& CharacterModificationUtils.tattoo.getCounter().getType()==TattooCounterType.NONE) {
					return new Response("应用", "你需要选择一种纹身类型、添加一些文字或添加一个计数器才能制作纹身！", null);
					
				} else {
					return new Response("应用", 
							UtilText.parse(BodyChanging.getTarget(), "添加这个纹身。"), CHOOSE_ADVANCED_APPEARANCE_TATTOOS) {
						@Override
						public void effects() {
							Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenPField').innerHTML=document.getElementById('tattoo_name').value;");
							CharacterModificationUtils.tattoo.getWriting().setText(Main.mainController.getWebEngine().getDocument().getElementById("hiddenPField").getTextContent());
							CharacterModificationUtils.tattoo.setName(CharacterModificationUtils.tattoo.getType().getName());
							BodyChanging.getTarget().addTattoo(CharacterModificationUtils.tattooInventorySlot, CharacterModificationUtils.tattoo);
						}
					};
				}
			
			} else if(index==2) {
				return new Response("保存/加载", "保存/加载纹身预设。", CosmeticsDialogue.TATTOO_SAVE_LOAD) {
					@Override
					public void effects() {
						CosmeticsDialogue.initTattooSaveLoadDialogue(CHOOSE_ADVANCED_APPEARANCE_TATTOOS_ADD);
					}
				};
			
			} else if(index==0) {
				return new Response("返回", "决定不纹这个纹身并返回主选择界面。", CHOOSE_ADVANCED_APPEARANCE_TATTOOS);
			}
			
			return null;
		}

		@Override
		public boolean reloadOnRestore() {
			return true;
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_COSMETICS = new DialogueNode("化妆品", "", true) {
		
		@Override
		public String getHeaderContent() {
			return "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>所有这些选项都可以在游戏后期改变。</i>"
					+ "</div>"
							
					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_BLUSHER, "腮红", "腮红（也称为胭脂）用于为脸颊上色，以提供更年轻的外观，并强调颧骨。", true, false)
					
					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_LIPSTICK, "口红", "口红用于为使用者的嘴唇提供颜色、质感和保护。", true, false)

					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_EYE_LINER, "眼线", "眼线画在眼睛轮廓周围，以帮助定义形状或突出不同的特征。", true, false)

					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_EYE_SHADOW, "眼影", "眼影用于让使用者的眼睛更突出或看起来更具吸引力。", true, false)

					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, "指甲油", "指甲油用于为你的[pc.hands]上的指甲上色和保护。", true, false)

					+CharacterModificationUtils.getKatesDivCoveringsNew(
							false, Race.NONE, BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, "趾甲油", "趾甲油用于为你的[pc.feet]上的趾甲上色和保护。", true, false);
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CHOOSE_ADVANCED_APPEARANCE_BODY_HAIR = new DialogueNode("体毛", "", true) {
		
		@Override
		public String getHeaderContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>"
												+ "<i>所有这些选项都可以在游戏后期改变。</i>"
											+ "</div>");
			
			if(Main.game.isPubicHairEnabled() || Main.game.isFacialHairEnabled() || Main.game.isBodyHairEnabled()) {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(
						false, Race.NONE, Main.game.getPlayer().getBodyHairCoveringType(), "体毛", "这是覆盖头部以外所有区域的毛发。", false, false, false));
			} else {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivGenericBodyHairDisabled(
						"体毛", "这是覆盖头部以外所有区域的毛发。", "所有额外的体毛选项都已禁用。你将不会看到任何额外的毛发内容。"));
				
				return UtilText.nodeContentSB.toString();
			}
			
			if(Main.game.isFacialHairEnabled()) {
				if (Main.game.isFemaleFacialHairEnabled()) {
					UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivFacialHair(false, "面部毛发", "你脸上的体毛。"));
				} else {
					UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivFacialHair(false, "面部毛发", "你脸上的体毛。女性角色无法长出面部毛发。"));
				}
			} else {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivGenericBodyHairDisabled(
						"面部毛发", "你脸上的体毛。女性角色无法长出面部毛发。", "面部毛发当前在选项中被禁用。禁用期间你将不会看到任何面部毛发内容。"));
			}
			
			if(Main.game.isPubicHairEnabled()) {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivPubicHair(false, "阴毛", "生殖器区域的体毛；位于你的性器官和胯部及其周围。"));
				
			} else {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivGenericBodyHairDisabled(
						"阴毛", "生殖器区域的体毛；位于你的性器官和胯部及其周围。", "阴毛当前在选项中被禁用。禁用期间你将不会看到任何阴毛内容。"));
			}
			
			if(Main.game.isBodyHairEnabled()) {
				UtilText.nodeContentSB.append(
						CharacterModificationUtils.getKatesDivUnderarmHair(false, "腋毛", "你腋下的体毛。"));
				
			} else {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivGenericBodyHairDisabled(
						"腋毛", "你腋下的毛发。", "腋毛当前在选项中被禁用。禁用期间你将不会看到任何腋毛内容。"));
			}
			
			if(Main.game.isAssHairEnabled()) {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivAssHair(false, "肛毛", "你肛门周围的体毛。"));
				
			} else {
				UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivGenericBodyHairDisabled(
						"肛毛", "你肛门周围的体毛。", "肛毛当前在选项中被禁用。禁用期间你将不会看到任何肛毛内容。"));
			}
			
			return UtilText.nodeContentSB.toString();
		}
		
		@Override
		public String getContent() {
			return "";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "确认你的选择并返回内容偏好菜单。", CHOOSE_ADVANCED_APPEARANCE);
				
			} else {
				return null;
			}
		}
	};
	
	public static String getCheckingClothingDescription() {
		StringBuilder sb = new StringBuilder();

		File dir = new File("res/");
		if(!dir.exists()) {
			sb.append("<p style='text-align:center;'>"
						+ "[style.italicsBad(游戏无法读取'res'文件夹，因此，关键的衣物物品将会丢失！在继续之前，请参考README.txt文件中的'MISSING FOLDERS'部分！)]"
					+ "</p>");
		}
		
		sb.append("<div class='container-full-width' style='background:transparent;'>"
					+ "<p>"
						+ "主舞台上似乎没有任何活动的迹象，所以，趁着还有几分钟时间，你决定整理一下自己的衣服。"
						+ " 毕竟，今晚对莉莉来说是个重要的夜晚，你希望她看到你为自己的外表下了一番功夫。"
					+ "</p>"
					+ "<p>"
						+ "你在镜子前转来转去，想更好地看看自己，你开始注意到今晚的自己是多么的"+(Main.game.getPlayer().isFeminine()?"性感":"英俊")+"……"
					+ "</p>"
					+ "<p>"
						+ "[pc.thought(为什么我突然感觉这么性奋？)]"
					+ "</p>"
					+ "<div class='container-full-width' style='text-align:center;'>"
						+ "<i>选择你决定穿去博物馆的衣服。</i><br/>"
						+ "<i>你需要穿着某种鞋子，以及遮盖生殖器和胸部的衣物，才能继续。</i>"
					+ "</div>"
				+ "</div>");
		
		return sb.toString();
	}
	
	public static void moveNPCIntoPlayerTile() {
		if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC || (Main.game.getPlayer().getSexualOrientation()==SexualOrientation.AMBIPHILIC && Main.game.getPlayer().hasVagina())) {
			Main.game.getNpc(PrologueMale.class).setLocation(Main.game.getPlayer().getWorldLocation(), Main.game.getPlayer().getLocation(), false);
			
		} else {
			Main.game.getNpc(PrologueFemale.class).setLocation(Main.game.getPlayer().getWorldLocation(), Main.game.getPlayer().getLocation(), false);
			Main.game.getNpc(PrologueFemale.class).addStatusEffect(StatusEffect.PROMISCUITY_PILL_PROLOGUE, 60*60*24*3); // 3 days
		}
	}
	
	public static void moveNPCOutOfPlayerTile() {
		Main.game.getNpc(PrologueMale.class).setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
		Main.game.getNpc(PrologueFemale.class).setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
	}
	
	public static boolean femalePrologueNPC() {
		return Main.game.getPlayer().getSexualOrientation()==SexualOrientation.GYNEPHILIC || (Main.game.getPlayer().getSexualOrientation()==SexualOrientation.AMBIPHILIC && Main.game.getPlayer().hasPenis());
	}
	
	public static final DialogueNode CHOOSE_BACKGROUND = new DialogueNode("在博物馆里", "-", true) {
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>"
						+ "你对自己的外表感到满意，转身离开镜子，开始朝主舞台走去。"
						+ " 每走一步，你都莫名其妙地发现自己越来越兴奋，当你走到离熙熙攘攘的参观人群还不到一半路程的时候，"
							+(Main.game.getPlayer().hasPenis()
									?" 你正努力克制自己不要勃起。"
									:" 你能感觉到自己的小穴因兴奋而湿润了。")
					+ "</p>"
					+ "<p>"
						+ "你躲到附近一根柱子后面，摇了摇头，试图驱散开始渗入脑海的肮脏念头。"
						+ " 当你靠在冰冷的石头上深吸一口气时，一个声音突然打断了你的思绪，");
			
			if(!femalePrologueNPC()) {
				UtilText.nodeContentSB.append(" [prologueMale.speech(你也想从人群中休息一下吗？)]"
						+ "</p>"
						+ "<p>"
							+ "你转过身，看到一个高大英俊的男人，他看起来只比你大几岁，正对着你露出你所见过的最迷人的微笑。"
							+ " 在你意识到自己在做什么之前，你的目光已经在他充满男子气概的肌肉身体的每一寸[unit.size]上下游移，你勉强忍住，才没有发出一声渴望的低吟。"
						+ "</p>"
						+ "<p>"
							+ "[pc.thought(集中精神，[pc.name]，集中精神！)] 你心想，一边尽量表现得自然，一边对着面前的陌生人回以微笑。"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(其实，)] 你说，[pc.speech(我刚到。我以为我要迟到了，但看起来什么都还没开始。)]"
						+ "</p>"
						+ "<p>"
							+ "[prologueMale.speech(啊，你肯定刚错过了通知，)] 他回答说，[prologueMale.speech(开幕演讲推迟了半个小时。"
								+ " 我试着在那边的人群里待了一会儿，但我不是历史学家，大部分对话都相当枯燥……)]"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(哈哈，)]"
							+ " 你笑着，拼命地不去想象他裸体的样子，"
							+ " [pc.speech(我<i>完全</i>明白你的意思。我阿姨就是做开幕演讲的那位女士，每次我见到她博物馆的朋友，我都跟不上他们的谈话。"
									+ " 嗯，除了亚瑟。他跟我们年龄相仿，而且人很随和，聊起天来也很有趣。)]"
						+ "</p>"
						+ "<p>"
							+ "[prologueMale.speech(哈！你认识亚瑟？我是应他的邀请来的。我们认识很久了，)]"
							+ " 那个男人愉快地回答，他的微笑让你的心跳加速。"
							+ " [prologueMale.speech(顺便一提，我叫[prologueMale.name]，很高兴认识你，"+(Main.game.getPlayer().isFeminine()?"……女士？":"……先生？")+")]"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(彼此彼此，)] 你回应道，握住他伸出的手，同时努力不去想他的握力是多么强而有力。[pc.speech(我叫[pc.Name]。)]"
						+ "</p>"
						+ "<p>"
							+ "在等待演讲开始的时候，你和[prologueMale.name]继续交谈。"
							+ " 不久，话题转向了工作，你发现他是一名航空公司飞行员，工作地点在城市郊区的机场。"
							+ " 接着，谈话转向了你的工作，你们就此聊了一会儿……"
						+ "</p>");
				
			} else {
				UtilText.nodeContentSB.append(" [prologueFemale.speech(你也想从人群中休息一下吗？)]"
						+ "</p>"
						+ "<p>"
							+ "你转过身，看到一个美丽的女人，看起来和你年龄相仿，正对着你露出你所见过的最惊艳的微笑。"
							+ " 在你意识到自己在做什么之前，你的目光已经在她婀娜多姿、充满女人味的身体的每一寸[unit.size]上下游移，你勉强忍住，才没有发出一声饥渴的呻吟。"
						+ "</p>"
						+ "<p>"
							+ "[pc.thought(集中精神，[pc.name]，集中精神！)] 你心想，一边尽量表现得自然，一边对着面前的陌生人回以微笑。"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(其实，)] 你说，[pc.speech(我刚到。我以为我要迟到了，但看起来什么都还没开始。)]"
						+ "</p>"
						+ "<p>"
							+ "[prologueFemale.speech(啊，你肯定刚错过了通知，)] 她回答说，[prologueFemale.speech(开幕演讲推迟了半个小时。"
								+ " 我试着在那边的人群里待了一会儿，但我不是历史学家，大部分对话都相当枯燥……)]"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(哈哈，)]"
							+ " 你笑着，拼命地不去想象她裸体的样子，"
							+ " [pc.speech(我<i>完全</i>明白你的意思。我阿姨就是做开幕演讲的那位女士，每次我见到她博物馆的朋友，我都跟不上他们的谈话。"
									+ " 嗯，除了亚瑟。他跟我们年龄相仿，而且人很随和，聊起天来也很有趣。)]"
						+ "</p>"
						+ "<p>"
							+ "[prologueFemale.speech(哦！你认识亚瑟？其实我是应他的邀请来的。我们认识很久了，)]"
							+ " 那个女人愉快地回答，她的微笑让你的心跳加速。"
							+ " [prologueFemale.speech(顺便一提，我叫[prologueFemale.name]，很高兴认识你，"+(Main.game.getPlayer().isFeminine()?"……女士？":"……先生？")+")]"
						+ "</p>"
						+ "<p>"
							+ "[pc.speech(彼此彼此，)] 你回应道，握住她伸出的手，同时努力不去想她的皮肤是多么柔软细腻。[pc.speech(我叫[pc.Name]。)]"
						+ "</p>"
						+ "<p>"


							+ "你和[prologueFemale.name]一边等着演讲开始，一边继续互相交谈。"
							+ " 不久之后，话题转移到了工作上，你发现她正在接受培训，准备成为一名医生，并且正在本市的大学里学习。"
							+ " 接着，话题又转到了你的工作上，你们就此聊了一会儿……"
						+ "</p>");
			}
			
			return UtilText.nodeContentSB.toString();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new ResponseEffectsOnly("返回", "返回服装选择。") {
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_BACKGROUND;
					}
					@Override
					public void effects() {
						moveNPCOutOfPlayerTile();
						InventoryDialogue.setBuyback(false);
						InventoryDialogue.setInventoryNPC(null);
						InventoryDialogue.setNPCInventoryInteraction(InventoryInteraction.CHARACTER_CREATION);
						Main.game.setContent(new Response("", "", InventoryDialogue.INVENTORY_MENU));
					}
				};
				
			} else if (index == 1) {
				return new Response("选择职业", "进入职业选择界面。", BACKGROUND_SELECTION_MENU) {
					@Override
					public int getSecondsPassed() {
						return TIME_TO_JOB;
					}
				};
				
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode BACKGROUND_SELECTION_MENU = new DialogueNode("在博物馆里", "-", true) {
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append("<div class='container-full-width'>"
									+ "<h6 style='text-align:center'>职业选择</h6>"
									+ "<p style='text-align:center'>点击你想要的职业旁边的图标，然后选择“继续”。</p>"
								+ "</div>");

			UtilText.nodeContentSB.append("<div class='container-full-width'>");
			for(Occupation history : Occupation.getAvailableHistories(Main.game.getPlayer())) {
				UtilText.nodeContentSB.append(
						"<div class='container-full-width'>"
							+"<div class='container-full-width' style='margin:0;padding:0;'>"
								+ "<h6 style='color:"+history.getAssociatedPerk().getColour().toWebHexString()+";'>"+Util.capitaliseSentence(history.getName(Main.game.getPlayer()))+"</h6>"
							+ "</div>"
							+"<div class='container-full-width' style='margin:0 8px; width: calc(10% - 16px);'>"
								+ "<div id='OCCUPATION_" + history + "' class='fetish-icon full"
									+ (Main.game.getPlayer().getHistory()==history
										? " owned' style='border:2px solid " + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>"
										: " unlocked' style='border:2px solid " + PresetColour.TEXT_GREY.toWebHexString() + ";" + "'>")
									+ "<div class='fetish-icon-content'>"+history.getAssociatedPerk().getSVGString(Main.game.getPlayer())+"</div>"
								+ "</div>"
							+ "</div>"
							+"<div class='container-full-width' style='margin:0 8px; width: calc(90% - 16px);'>"
								+ "<p>"
									+ history.getDescription(Main.game.getPlayer())
								+ "</p>"
							+ "</div>"
						+ "</div>");
			}
			
			UtilText.nodeContentSB.append("</div>");
			
			return UtilText.nodeContentSB.toString();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("返回", "返回上一个界面。", CHOOSE_BACKGROUND) {
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_JOB;
					}
				};
				
			} else if (index == 1) {
				if(Main.game.getPlayer().getHistory().getAssociatedPerk()==null) {
					return new Response("继续", "你需要先选择一个职业才能继续！", null);
				} else {
					return new Response("继续", femalePrologueNPC()?"告诉[prologueFemale.name]你是做什么的。":"告诉[prologueMale.name]你是做什么的。", CHOOSE_SEX_EXPERIENCE) {
						@Override
						public int getSecondsPassed() {
							return TIME_TO_SEX_EXPERIENCE;
						}
						@Override
						public void effects() {
							Main.game.getPlayer().getVirginityLossMap().replaceAll((k, v) ->
								(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.GYNEPHILIC
									|| (Main.game.getPlayer().getSexualOrientation()==SexualOrientation.AMBIPHILIC && !Main.game.getPlayer().isFeminine()))
									?new SimpleEntry<>("", "你的女朋友")
									:new SimpleEntry<>("", "你的男朋友"));
						}
					};
				}
				
			} else {
				return null;
			}
		}
	};
	
	
	public static final DialogueNode CHOOSE_SEX_EXPERIENCE = new DialogueNode("开始", "", true) {
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>");
			switch(Main.game.getPlayer().getHistory()) {
				case ATHLETE:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是一名职业运动员，)]"
							+ " 你解释道，"
							+ " [pc.speech(大部分时间都在为参加比赛而训练。)]");
					break;
				case BUTLER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我在城里一个很有影响力的家庭当管家，)]"
							+ " 你解释道，"
							+ " [pc.speech(但我今晚请了假，这样我才能来参加莉莉的演讲。)]");
					break;
				case CHEF:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是这附近一家餐厅的主厨，)]"
							+ " 你解释道，"
							+ " [pc.speech(但我今晚请了假，这样我才能来参加莉莉的演讲。)]");
					break;
				case CONSTRUCTION_WORKER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是一名建筑工人，)]"
							+ " 你解释道，"
							+ " [pc.speech(目前正在管理市郊的一个大型项目。)]");
					break;
				case MAID:
					UtilText.nodeContentSB.append(
							"[pc.speech(我在城里一个很有影响力的家庭当女仆长，)]"
							+ " 你解释道，"
							+ " [pc.speech(但我今晚请了假，这样我才能来参加莉莉的演讲。)]");
					break;
				case MUSICIAN:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是市交响乐团的成员，)]"
							+ " 你解释道，"
							+ " [pc.speech(我也会做一些私人音乐辅导。)]");
					break;
				case OFFICE_WORKER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我在市中心的一家公司办公室工作，)]"
							+ " 你解释道，"
							+ " [pc.speech(主要做一些行政和文书工作。)]");
					break;
				case SOLDIER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我在军队服役，)]"
							+ " 你解释道，"
							+ " [pc.speech(这周剩下的时间我都在休假，之后就得回军营了。)]");
					break;
				case STUDENT:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是市立大学的学生，)]"
							+ " 你解释道，"
							+ " [pc.speech(虽然我还没想好要主修什么专业。)]");
					break;
				case TEACHER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我在本地一所中学当老师，)]"
							+ " 你解释道，"
							+ " [pc.speech(不过这周是期中假期，我可以好好放松一下。)]");
					break;
				case UNEMPLOYED:
					UtilText.nodeContentSB.append(
							"[pc.speech(我目前待业中，)]"
							+ " 你解释道，"
							+ " [pc.speech(实际上我一直在考虑申请来这家博物馆工作。)]");
					break;
				case WRITER:
					UtilText.nodeContentSB.append(
							"[pc.speech(我是一名职业作家，)]" // I write erotic novels...
							+ " 你解释道，"
							+ " [pc.speech(目前正在等我的出版商关于我最新一部小说的回音。)]");
					break;
				case ARISTOCRAT:
					UtilText.nodeContentSB.append(
							"[pc.speech(我不需要为工作操心，)]"
							+ " 你解释道，"
							+ " [pc.speech(我的家族产业为我提供了所需的所有收入，所以我把时间都花在旅行和享受生活上。)]");
					break;
				case TOURIST:
					UtilText.nodeContentSB.append(
							"[pc.speech(我来这里度假，)]"
							+ " 你解释道，"
							+ " [pc.speech(在英国的这段时间里，我可不想考虑工作的事。)]");
					break;
				default:
					break;
			}
			UtilText.nodeContentSB.append("</p>");
			
			if(femalePrologueNPC()) {
				UtilText.nodeContentSB.append(
						"<p>"
							+ "当你们继续交谈时，先是聊工作，然后是更宽泛的话题，你发现自己越来越兴奋了。"
							+ " 更重要的是，你开始注意到[prologueFemale.name]的脸颊开始泛红，她还趁你没注意时，用饥渴的目光偷瞄你的身体。"
						+ "</p>"
						+ "<p>"
							+ "她开始公开谈论她的性生活，这最终证明了她和你一样兴奋。"
							+ " 一开始，你对她的开放感到有些吃惊，但随着她谈得越多，你发现自己也越能和这个素不相识的人自在地谈论性。"
						+ "</p>"
						+ "<p>"
							+ "于是，在和[prologueFemale.name]交谈了不到十分钟后，你就把自己的性经历事无巨细地都告诉了她……"
						+ "</p>");
				
			} else {
				UtilText.nodeContentSB.append(
						"<p>"
							+ "当你们继续交谈时，先是聊工作，然后是更宽泛的话题，你发现自己越来越兴奋了。"
							+ " 更重要的是，你开始注意到[prologueMale.name]的脸颊开始泛红，他还趁你没注意时，用饥渴的目光偷瞄你的身体。"
						+ "</p>"
						+ "<p>"
							+ "他开始公开谈论他的性生活，这最终证明了他和和你一样兴奋。"
							+ " 一开始，你对他的开放感到有些吃惊，但随着他谈得越多，你发现自己也越能和这个素不相识的人自在地谈论性。"
						+ "</p>"
						+ "<p>"
							+ "于是，在和[prologueMale.name]交谈了不到十分钟后，你就把自己的性经历事无巨细地都告诉了他……"
						+ "</p>");
			}
			
			UtilText.nodeContentSB.append(
						"<div class='container-full-width' style='text-align:center;'>"
							+ "<i>更多的性经验会导致堕落值的增加。（你可以在屏幕左侧的角色面板中查看你的堕落值以及其他属性。）"
							+ "<br/>"
							+ "为任何性癖欲望选择‘<span style='color:"+FetishDesire.FOUR_LOVE.getColour().toWebHexString()+";'>"+FetishDesire.FOUR_LOVE.getName()+"</span>’"
								+ "将使你的角色在游戏开始时就拥有该性癖，而其他四个欲望只是决定你的角色对该性癖的态度。</i>"
						+ "</div>"
						+CharacterModificationUtils.getSexualExperienceDiv()
						+CharacterModificationUtils.getFetishChoiceDiv());
			
			return UtilText.nodeContentSB.toString();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("继续", "当你对自己的性经验设定感到满意后，便可进入角色创建的最后一部分。", FINAL_CHECK) {
					@Override
					public int getSecondsPassed() {
						return TIME_TO_FINAL_CHECK;
					}
					@Override
					public void effects() {
						if(!Main.game.getPlayer().hasPenis()) {
							for(SexAreaOrifice ot : SexAreaOrifice.values()) {
								SexType st = new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, ot);
								Main.game.getPlayer().resetVirginityLoss(st);
								st = new SexType(SexParticipantType.SELF, SexAreaPenetration.PENIS, ot);
								Main.game.getPlayer().resetVirginityLoss(st);
							}
							Main.game.getPlayer().setPenisVirgin(true);
							
						}
						if(!Main.game.getPlayer().hasVagina()) {
							for(SexAreaPenetration pt : SexAreaPenetration.values()) {
								SexType st = new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, pt);
								Main.game.getPlayer().resetVirginityLoss(st);
								st = new SexType(SexParticipantType.SELF, SexAreaOrifice.VAGINA, pt);
								Main.game.getPlayer().resetVirginityLoss(st);
							}
							Main.game.getPlayer().setVaginaVirgin(true);
						}
					}
				};
				
			} else if (index == 0) {
				return new Response("返回", "返回职业选择。", BACKGROUND_SELECTION_MENU) {
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_SEX_EXPERIENCE;
					}
				};
				
			} else {
				return null;
			}
		}
	};

	private static void applyGameStart() {
		CharacterModificationUtils.resetImpossibeSexExperience();
		
		Main.getProperties().addRaceDiscovered(Subspecies.HUMAN);
		Main.game.getPlayer().setGenderIdentity(Main.game.getPlayer().getGender());
		
		Main.game.getNpc(Lilaya.class).setSkinCovering(new Covering(BodyCoveringType.HUMAN, Main.game.getPlayer().getCovering(BodyCoveringType.HUMAN).getPrimaryColour()), true);

		Main.game.getNpc(Lilaya.class).setBirthday(LocalDateTime.of(Main.game.getPlayer().getBirthday().getYear()-22+18, Main.game.getNpc(Lilaya.class).getBirthMonth(), Main.game.getNpc(Lilaya.class).getDayOfBirth(), 12, 0));
		
		Main.game.clearTextStartStringBuilder();
		Main.game.clearTextEndStringBuilder();

		Main.game.setWeatherInSeconds(Weather.MAGIC_STORM, 5*60*60);
		
		Main.game.getPlayerCell().resetInventory();

		Main.game.getPlayer().addItem(Main.game.getItemGen().generateItem("innoxia_quest_clothing_keys"), false);
	}

	private static void applySkipPrologueStart(boolean imported) {
		Main.game.getPlayer().addCharacterEncountered(Main.game.getNpc(Lilaya.class));
		Main.game.getPlayer().addCharacterEncountered(Main.game.getNpc(Rose.class));
		
		Main.getProperties().addRaceDiscovered(Main.game.getNpc(Lilaya.class).getSubspecies());
		Main.getProperties().addRaceDiscovered(Main.game.getNpc(Rose.class).getSubspecies());
		
		Main.game.applyStartingDateChange();
		if(!imported) {
			Main.game.getPlayer().setAgeAppearanceDifference(-Game.TIME_SKIP_YEARS);
		}

		Main.game.getPlayer().addSpecialPerk(Perk.SPECIAL_PLAYER);
		
		moveNPCOutOfPlayerTile();
	}
	
	public static final DialogueNode FINAL_CHECK = new DialogueNode("开始", "", true) {
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			UtilText.nodeContentSB.append(
				"<div class='container-full-width' style='text-align:center;'>"
					+ "<i>当您对自己的外观感到满意后，请按“开始游戏”按钮开始！<br/>"
					+ "[style.colourGood(这是角色创建的最后一步，请在对您的选择满意后再继续！)]</i>"
				+ "</div>"
				+ "<br/>"
				+ "<div class='container-full-width'>"
					+ "<h5 style='text-align:center;'>最终外观</h5>"
					+ Main.game.getPlayer().getBodyDescription()
				+ "</div>");
			
			return UtilText.nodeContentSB.toString();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("开始游戏", "使用此角色开始游戏，从在博物馆里寻找亚瑟开始。", PrologueDialogue.INTRO){
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.MAIN));
						
						applyGameStart();
					}
				};
				
			} else if (index == 2) {
				return new ResponseEffectsOnly("跳过序章", "开始游戏并跳过序章。<br/><br/><i style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>不建议首次游玩时使用！</i>"){
					@Override
					public int getSecondsPassed() {
						return 60*60;
					}
					@Override
					public void effects() {
						
						Main.game.setRenderMap(true);
						
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.MAIN));
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.MAIN, Quest.MAIN_1_A_LILAYAS_TESTS));
						
						Main.game.getPlayer().incrementMoney(5000);

						DamageType damageType = DamageType.FIRE;
						switch(CharacterCreation.getStartingDemonstoneSpellSchool()) {
							case AIR:
								damageType = DamageType.POISON;
								break;
							case EARTH:
								damageType = DamageType.PHYSICAL;
								break;
							case ARCANE:
							case FIRE:
								damageType = DamageType.FIRE;
								break;
							case WATER:
								damageType = DamageType.ICE;
								break;
						}
						if(Main.game.getPlayer().getMainWeapon(0)==null) {
							Main.game.getPlayer().equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("innoxia_crystal_rare", damageType));
						} else {
							Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("innoxia_crystal_rare", damageType), false);
						}
						
						Spell startingSpell = Spell.FIREBALL;
						switch(getStartingTomeSpellSchool()) {
							case AIR:
								startingSpell = Spell.POISON_VAPOURS;
								break;
							case EARTH:
								startingSpell = Spell.SLAM;
								break;
							case FIRE:
							case ARCANE:
								startingSpell = Spell.FIREBALL;
								break;
							case WATER:
								startingSpell = Spell.ICE_SHARD;
								break;
						}
						AbstractItem spellBook = Main.game.getItemGen().generateItem(ItemType.getSpellBookType(startingSpell));
						Main.game.getWorlds().get(WorldType.LILAYAS_HOUSE_FIRST_FLOOR).getCell(PlaceType.LILAYA_HOME_ROOM_PLAYER).getInventory().addItem(spellBook);
						
						applyGameStart();
						applySkipPrologueStart(false);
						Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, PlaceType.LILAYA_HOME_ROOM_PLAYER);

						Main.game.getPlayer().setHealth(Main.game.getPlayer().getAttributeValue(Attribute.HEALTH_MAXIMUM));
						Main.game.getPlayer().setMana(Main.game.getPlayer().getAttributeValue(Attribute.MANA_MAXIMUM));
						Main.game.getPlayer().setLustNoText(Main.game.getPlayer().getRestingLust());
						
						Main.game.setContent(new Response("", "", Main.game.getDefaultDialogue(false)));
					}
				};
				
			} else if (index == 0) {
				return new Response("返回", "返回性经验选择。", CHOOSE_SEX_EXPERIENCE){
					@Override
					public int getSecondsPassed() {
						return -TIME_TO_FINAL_CHECK;
					}
					@Override
					public void effects() {
						// Remove attribute gain sentences in the start game screen:
						Main.game.clearTextEndStringBuilder();
					}
				};
				
			} else {
				return null;
			}
		}
	};
	
	
	private static StringBuilder importSB;
	public static final DialogueNode IMPORT_CHOOSE = new DialogueNode("导入", "", true) {
		
		@Override
		public String getContent(){
			importSB = new StringBuilder();

			importSB.append("<p style='text-align:center;'>"
					+ "这些角色正在从 'data/characters' 文件夹中读取。"
					+ " 如果你想从旧版本导入角色，请按以下步骤操作：<br/><br/>"
					+ "<b>1.</b> 打开旧版游戏，导出你的旧角色（菜单 -> 选项 -> 导出）。<br/>"
					+ "<b>2.</b> 复制导出的 .xml 文件（位于旧版本的 <i>data/characters</i> 文件夹中）。<br/>"
					+ "<b>3.</b> 将其粘贴到此版本的 <i>data/characters</i> 文件夹中。<br/>"
					+ "<b>4.</b> 按下“刷新”，你的旧角色文件就会出现在下面的列表中！<br/><br/>"
//					+ "(If it doesn't work, please let me know as a comment on my blog, and I'll get it fixed ASAP!)"
					+ "</p>"
					+ "<p>"
					+ "<table align='center'>"
					+ "<tr>"
					+ "<th></th>"
					+ "<th>名称</th>"
					+ "<th></th>"
					+ "</tr>");
			
			int i=1;
			for(File f : Main.getCharactersForImport()){
				importSB.append(getImportRow(i, f.getName()));
				i++;
			}

			importSB.append("</table>"
					+ "</p>"
					+ "<p id='hiddenPField' style='display:none;'></p>");

			return importSB.toString();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("刷新", "刷新此页面。", IMPORT_CHOOSE);
				
			} else if (index == 0) {
				return new Response("返回", "返回主菜单。", OptionsDialogue.MENU);
				
			} else {
				return null;
			}
		}
	};
	private static String getImportRow(int i, String name) {
		String baseName = Util.getFileName(name);
		String identifier = Util.getFileIdentifier(name);
		
		return "<tr>"
				+ "<td>"
					+ i+"."
				+ "</td>"
				+ "<td style='min-width:200px;'>"
					+ baseName
				+ "</td>"
				+ "<td>"
					+ "<div class='saveLoadButton' id='IMPORT_CHARACTER_" + identifier + "' style='color:"+PresetColour.GENERIC_GOOD.toWebHexString()+";'>加载</div>"
				+ "</td>"
				+ "</tr>";
	}

	private static boolean resetImportedCharacter = false;

	public static final DialogueNode START_GAME_WITH_IMPORT = new DialogueNode("开始游戏", "", true) {
		
		@Override
		public String getLabel() {
			return "导入的角色";
		}
		
		@Override
		public String getContent() {
			return "<p>"
						+ "<b>待办：</b> 未来某个时候，我会为导入的角色启用完整的角色创建流程！"
					+ "</p>"
					+ "<br/>"
					+"<details>"
						+ "<summary class='quest-title' style='color:" + QuestType.MAIN.getColour().toWebHexString() + ";'>导入日志</summary>"
						+ Main.game.getCharacterUtils().getCharacterImportLog()
					+ "</details>"
					+ "<div class='container-full-width'>"
						+ "<h5 style='text-align:center;'>外观</h5>"
						+ Main.game.getPlayer().getBodyDescription()
					+ "</div>";
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("开始", "使用此角色从头开始游戏。", INTRO_2_FROM_IMPORT){
					@Override
					public void effects() {
						if(resetImportedCharacter){
							resetPlayerCharacter();
						}
						Main.game.getPlayer().resetAllQuests();
						Main.game.getPlayer().getCharactersEncountered().clear();
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.MAIN));
						applyGameStart();
					}
				};
				
			} else if (index == 2) {
				return new ResponseEffectsOnly("跳过序章", "开始游戏并跳过序章。<br/><br/><i style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>不建议首次游玩时使用！</i>"){
					@Override
					public void effects() {
						Main.game.setRenderMap(true);
						if(resetImportedCharacter){
							resetPlayerCharacter();
						}
						Main.game.getPlayer().incrementMoney(5000);

						Main.game.getPlayer().resetAllQuests();
						Main.game.getPlayer().getCharactersEncountered().clear();
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.MAIN));
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.MAIN, Quest.MAIN_1_A_LILAYAS_TESTS));

						DamageType damageType = DamageType.FIRE;
						switch(CharacterCreation.getStartingDemonstoneSpellSchool()) {
							case AIR:
								damageType = DamageType.POISON;
								break;
							case EARTH:
								damageType = DamageType.PHYSICAL;
								break;
							case ARCANE:
							case FIRE:
								damageType = DamageType.FIRE;
								break;
							case WATER:
								damageType = DamageType.ICE;
								break;
						}
						if(Main.game.getPlayer().getMainWeapon(0)==null) {
							Main.game.getPlayer().equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("innoxia_crystal_rare", damageType));
						} else {
							Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("innoxia_crystal_rare", damageType), false);
						}
						
						AbstractItem spellBook = Main.game.getItemGen().generateItem(ItemType.getSpellBookType(Spell.FIREBALL));
						if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 1) {
							spellBook = Main.game.getItemGen().generateItem(ItemType.getSpellBookType(Spell.SLAM));
						} else if(Main.game.getPlayer().getBirthMonth().getValue() % 4 == 2) {
							spellBook = Main.game.getItemGen().generateItem(ItemType.getSpellBookType(Spell.POISON_VAPOURS));
						} else if(Main.game.getPlayer().getBirthMonth().getValue()  % 4 == 3) {
							spellBook = Main.game.getItemGen().generateItem(ItemType.getSpellBookType(Spell.ICE_SHARD));
						}
						Main.game.getWorlds().get(WorldType.LILAYAS_HOUSE_FIRST_FLOOR).getCell(PlaceType.LILAYA_HOME_ROOM_PLAYER).getInventory().addItem(spellBook);
						
						applyGameStart();
						applySkipPrologueStart(true);
						Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, PlaceType.LILAYA_HOME_ROOM_PLAYER);


						Main.game.setContent(new Response("", "", Main.game.getDefaultDialogue(false)));
					}
				};

			} else if (index == 5) {
				return new ResponseEffectsOnly(resetImportedCharacter
						?"重置角色：<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>开启</span>"
						:"重置角色：<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>关闭</span>",
						"将经验值和火焰重置为0，并清空你背包中的所有物品，已装备的衣物和武器除外。 " +
								"法术和法术天赋也将被移除。"){
					@Override
					public void effects(){
						resetImportedCharacter = !resetImportedCharacter;
					}
				};


			}
			// Throws error when going back and then resuming
//			else if (index == 0) {
//				return new Response("返回", "返回新游戏界面。", OptionsDialogue.MENU);
//			}
			else {
				return null;
			}
		}
	};

	private static void resetPlayerCharacter(){
		PlayerCharacter player = Main.game.getPlayer();
		player.clearNonEquippedInventory(true);
		player.setEssenceCount(0);
		player.incrementExperience(player.getExperienceNeededForNextLevel(player.getLevel()), false);
		player.setLevel(1);
		player.resetSpells();
		player.resetPerksMap(false);
	}

	public static final DialogueNode INTRO_2_FROM_IMPORT = new DialogueNode("在博物馆里", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60*10;
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("misc/prologue", "INTRO_2");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return PrologueDialogue.INTRO_2.getResponse(responseTab, index);
		}
	};
	
}

