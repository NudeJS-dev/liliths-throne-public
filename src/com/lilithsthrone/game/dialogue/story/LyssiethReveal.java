
package com.lilithsthrone.game.dialogue.story;

import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.npc.dominion.Arthur;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.npc.dominion.Rose;
import com.lilithsthrone.game.character.npc.submission.DarkSiren;
import com.lilithsthrone.game.character.npc.submission.Elizabeth;
import com.lilithsthrone.game.character.npc.submission.Lyssieth;
import com.lilithsthrone.game.character.npc.submission.Takahashi;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.sex.managers.submission.SMLyssiethSex;
import com.lilithsthrone.game.sex.positions.SexPosition;
import com.lilithsthrone.game.sex.positions.slots.SexSlotStanding;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.Vector2i;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.3
 * @version 0.3.4
 * @author Innoxia
 */
public class LyssiethReveal {

	public static final DialogueNode ENTRANCE_WITH_ELIZABETH = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "ENTRANCE_WITH_ELIZABETH");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("跟随", "让伊丽莎白带你前往莉西斯蒂的王座厅。", FORWARDS_1) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(new Vector2i(Main.game.getPlayer().getLocation().getX(), Main.game.getPlayer().getLocation().getY()+1));
						Main.game.getNpc(Elizabeth.class).setLocation(Main.game.getPlayer(), false);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode FORWARDS_1 = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "FORWARDS_1");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("继续前进", "继续跟着伊丽莎白。", FORWARDS_2) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(new Vector2i(Main.game.getPlayer().getLocation().getX(), Main.game.getPlayer().getLocation().getY()+1));
						Main.game.getNpc(Elizabeth.class).setLocation(Main.game.getPlayer(), false);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode FORWARDS_2 = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "FORWARDS_2");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("进入", "你为面见莉西斯蒂做好准备，然后跟着伊丽莎白穿过敞开的大门。",
						FORWARDS_3) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(new Vector2i(Main.game.getPlayer().getLocation().getX(), Main.game.getPlayer().getLocation().getY()+1));
						Main.game.getNpc(Elizabeth.class).setLocation(WorldType.SUBMISSION, PlaceType.SUBMISSION_LILIN_PALACE_GATE);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode FORWARDS_3 = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "FORWARDS_3");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("莉西斯蒂的办公室", "让[siren.name]把你介绍给莉西斯蒂。", OFFICE) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(new Vector2i(Main.game.getPlayer().getLocation().getX(), Main.game.getPlayer().getLocation().getY()+1));
						Main.game.getNpc(DarkSiren.class).setLocation(Main.game.getPlayer(), false);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("防御", "看起来莉西斯蒂要攻击你了！保护好自己！", OFFICE_REACTION);
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE_REACTION = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_REACTION");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("抵抗", "不要屈服！抵抗莉西斯蒂的法术，努力保持站立。", OFFICE_REACTION_BETRAYAL) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_REACTION_RESIST"));
					}
				};
				
			} else if(index==2) {
				return new Response("屈服", "是的……跪下……就此屈服吧……", OFFICE_REACTION_BETRAYAL) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_REACTION_SUBMIT"));
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE_REACTION_BETRAYAL = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_REACTION_BETRAYAL");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("站起来", "照莉西斯蒂说的做，挣扎着站起来。", OFFICE_REACTION_END);
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE_REACTION_END = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_REACTION_END");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("向前", "移动到莉西斯蒂旁边，这样她就能把你和[siren.name]传送到莉拉娅的家。", OFFICE_TELEPORT);
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE_TELEPORT = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_TELEPORT");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("抵达", "你们三人抵达了莉西斯蒂的实验室。", OFFICE_TELEPORT_ARRIVE) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LAB);
						Main.game.getNpc(DarkSiren.class).setLocation(Main.game.getPlayer(), false);
						Main.game.getNpc(Lyssieth.class).setLocation(Main.game.getPlayer(), false);
						Main.game.getNpc(Arthur.class).setLocation(Main.game.getPlayer(), false);
						Main.game.getNpc(Lilaya.class).setLocation(Main.game.getPlayer(), false);
						Main.game.getNpc(Rose.class).setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, PlaceType.LILAYA_HOME_ROOM_ROSE);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode OFFICE_TELEPORT_ARRIVE = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "OFFICE_TELEPORT_ARRIVE");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("亚瑟", "听听亚瑟的理论。", LAB_ARTHUR_THEORY);
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_ARTHUR_THEORY = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ARTHUR_THEORY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("什么？！", "对莉西斯蒂刚刚说的话表示震惊。", LAB_WORLD_REVEAL);
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_WORLD_REVEAL = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_WORLD_REVEAL");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("莉拉娅", "虽然[siren.name]和亚瑟似乎很好地接受了这个消息，但莉拉娅看起来非常痛苦，而且她好像有话要说。", LAB_LILAYA_ANGERY);
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_LILAYA_ANGERY = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_LILAYA_ANGERY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("冷静", "安抚莉拉娅，向她保证她本质上还是你一直认识的那个人。", LAB_LILAYA_CALMED_DOWN);
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_LILAYA_CALMED_DOWN = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_LILAYA_CALMED_DOWN");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked1)) {
					return new Response("世界", "你已经问过莉西斯蒂关于世界其他地方的情况了。", null);
				} else {
					return new Response("世界", "询问莉西斯蒂为什么世界其他地方还没有采取任何行动。", LAB_QUESTION) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_WORLD"));
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lyssiethQuestionAsked1, true);
							AbstractItemEffectType.getBookEffect(Main.game.getPlayer(), Subspecies.LILIN, null, false);
						}
					};
				}
				
			} if(index==2) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked2)) {
					return new Response("背叛", "你已经问过莉西斯蒂为什么选择背叛莉莉丝了。", null);
				} else {
					return new Response("背叛", "询问莉西斯蒂为什么选择背叛莉莉丝。", LAB_QUESTION) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_BETRAYAL"));
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lyssiethQuestionAsked2, true);
						}
					};
				}
				
			} else if(index==3) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked3)) {
					return new Response("人们", "莉拉娅已经问过莉西斯蒂关于人们转变的事情了。", null);
				} else {
					return new Response("人们", "莉拉娅想问人们是如何被变成不同版本的自己的。", LAB_QUESTION) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_PEOPLE"));
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lyssiethQuestionAsked3, true);
						}
					};
				}
				
			} else if(index==4) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked4)) {
					return new Response("法术", "[siren.Name]已经问过莉西斯蒂关于莉莉丝那扭曲现实的法术了。", null);
				} else {
					return new Response("法术", "[siren.Name]想问关于莉莉丝那扭曲现实的法术。", LAB_QUESTION) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_SPELL"));
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lyssiethQuestionAsked4, true);
						}
					};
				}
				
			} else if(index==5) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked5)) {
					return new Response("逆转", "亚瑟已经问过莉西斯蒂逆转莉莉丝法术的可能性了。", null);
				} else {
					return new Response("逆转", "亚瑟想问逆转莉莉丝法术的可能性。", LAB_QUESTION) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_REVERSAL"));
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lyssiethQuestionAsked5, true);
						}
					};
				}
				
			} else if(index==6) {
				if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked1)
						|| !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked2)
						|| !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked3)
						|| !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked4)
						|| !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lyssiethQuestionAsked5)) {
					return new Response("继续", "在继续之前，你需要询问莉西斯蒂关于法术的事情。", null);
				} else {
					return new Response("继续", "莉西斯蒂没有更多时间回答问题了。", LAB_QUESTION_END);
				}
			}
			
			return null;
		}
	};
	
	public static final DialogueNode LAB_QUESTION = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return "";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return LAB_LILAYA_CALMED_DOWN.getResponse(responseTab, index);
		}
	};
	
	public static final DialogueNode LAB_QUESTION_END = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_QUESTION_END");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("解放", "告诉大家你想打败莉莉丝，结束她的暴政。", LAB_ENDING) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_LIBERATE"));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lyssieth.class).incrementAffection(Main.game.getPlayer(), 25));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lilaya.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(DarkSiren.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Arthur.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.firstReactionLiberate, true);
					}
				};
				
			} else if(index==2) {
				return new Response("篡夺", "表明你打算打败莉莉丝，并取代她成为多米尼恩的统治者。", LAB_ENDING) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_USURP"));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lyssieth.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lilaya.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(DarkSiren.class).incrementAffection(Main.game.getPlayer(), 10));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.firstReactionUsurp, true);
					}
				};
				
			} else if(index==3) {
				return new Response("加入", "说你想加入莉莉丝。", LAB_ENDING) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_JOIN"));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lyssieth.class).incrementAffection(Main.game.getPlayer(), -20));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lilaya.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(DarkSiren.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Arthur.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.firstReactionJoin, true);
					}
				};
				
			} else if(index==4) {
				return new Response("什么都不做",
						"你真的不在乎拯救世界之类的废话。什么都不做，把世界的命运留给更有热情的人，这样要省事得多。",
						LAB_ENDING) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_NOTHING"));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lyssieth.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Lilaya.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(DarkSiren.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getTextStartStringBuilder().append(Main.game.getNpc(Arthur.class).incrementAffection(Main.game.getPlayer(), -5));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.firstReactionNothing, true);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_ENDING = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("不可能", "询问莉西斯蒂，你要如何对抗一位远古莉莉因和她的恶魔半人马军团。", LAB_ENDING_MINOTALLYS);
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_ENDING_MINOTALLYS = new DialogueNode("", "", true, true) {
		@Override
		public int getSecondsPassed() {
			return 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_MINOTALLYS");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("妖狐？", "问梅拉西斯妖狐是谁。", LAB_ENDING_SIREN_HELP);
			}
			return null;
		}
	};

	public static final DialogueNode LAB_ENDING_SIREN_HELP = new DialogueNode("", "", true, true) {
		@Override
		public void applyPreParsingEffects() {
			Main.game.getNpc(Takahashi.class).setPlayerKnowsName(true);
		}
		@Override
		public int getSecondsPassed() {
			return 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_SIREN_HELP");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("返回", "和莉西斯蒂一起返回她的办公室，留下莉拉娅和[siren.name]。", LAB_ENDING_RETURN) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(WorldType.LYSSIETH_PALACE, PlaceType.LYSSIETH_PALACE_OFFICE);
						Main.game.getNpc(Lyssieth.class).setLocation(Main.game.getPlayer(), false);
						Main.game.getNpc(Arthur.class).returnToHome();
						Main.game.getNpc(Rose.class).setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LAB);
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode LAB_ENDING_RETURN = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_RETURN");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				if(Main.game.getPlayer().getRace()==Race.HUMAN) {
					return new Response("拒绝性爱",
							"告诉莉西斯蒂你不想做爱，如果她非得高潮不可，那她只能自慰了。",
							LAB_ENDING_RETURN_DECLINE_SEX) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_RETURN_DECLINE_SEX_HUMAN"));
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().incrementEssenceCount(250, false));
						}
					};
				} else {
					return new Response("拒绝性爱",
							"告诉莉西斯蒂你对和她做爱没兴趣，并允许她将力量注入你的灵气。",
							LAB_ENDING_RETURN_DECLINE_SEX) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "LAB_ENDING_RETURN_DECLINE_SEX"));
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().incrementEssenceCount(250, false));
						}
					};
				}
				
			} else if(index==2) {
				return new ResponseSex("小穴",
						"告诉莉西斯蒂你想和她做爱，并且想在主导位使用她的小穴。",
						true,
						true,
						new SMLyssiethSex(
								SexPosition.STANDING,
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotStanding.STANDING_DOMINANT)),
								Util.newHashMapOfValues(new Value<>(Main.game.getNpc(Lyssieth.class), SexSlotStanding.STANDING_SUBMISSIVE))),
						null,
						null,
						POWER_VISION,
						UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "SEX_PUSSY"));
				
			} else if(index==3) {
				return new ResponseSex("鸡巴",
						"告诉莉西斯蒂你想和她做爱，而且她应该长出一根鸡巴来主导你。",
						true,
						true,
						new SMLyssiethSex(
								SexPosition.STANDING,
								Util.newHashMapOfValues(new Value<>(Main.game.getNpc(Lyssieth.class), SexSlotStanding.STANDING_DOMINANT)),
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotStanding.STANDING_SUBMISSIVE))),
						null,
						null,
						POWER_VISION,
						UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "SEX_COCK")) {
					@Override
					public void effects() {


						((Lyssieth) Main.game.getNpc(Lyssieth.class)).growCock(PenisType.HUMAN);
					}
				};
			}
			return null;
		}
	};
	
//	private static void setPlayerAsLyssieth() {
//		PlayerCharacter player = new PlayerCharacter(
//				new NameTriplet("Lyssieth"),
//				1000,
//				null,
//				Gender.F_V_B_FEMALE,
//				Subspecies.DEMON,
//				RaceStage.GREATER,
//				WorldType.LYSSIETH_PALACE,
//				PlaceType.LYSSIETH_PALACE_OFFICE);
//		player.setSurname("Lilithmartuilani");
//		player.setDescription("作为七位莉莉姆长者之一，你是现存最强大的存在之一。");
//		player.setSubspeciesOverride(Subspecies.ELDER_LILIN);
//		player.getBody().calculateRace(player);
//		player.setAttribute(Attribute.MAJOR_PHYSIQUE, 100);
//		player.setAttribute(Attribute.MAJOR_ARCANE, 100);
//		player.setAttribute(Attribute.MAJOR_CORRUPTION, 100);
//		Main.game.setPlayer(player);
//	}
	
	public static final DialogueNode LAB_ENDING_RETURN_DECLINE_SEX = new DialogueNode("", "", true, true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}

		@Override
		public String getContent() {
			return "";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("幻象", "你看到了一个奇怪的幻象，在幻象中你就是莉茜斯……", POWER_VISION) {
					@Override
					public void effects() {
//						setPlayerAsLyssieth();
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode POWER_VISION = new DialogueNode("幻象", "你看到了一个奇怪的幻象，在幻象中你就是莉茜斯……", true) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "POWER_VISION");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("醒来", "你在莉茜斯的办公室里恢复了意识。", POWER_EXPLANATION) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().addSpecialPerk(Perk.POWER_OF_LYSSIETH_4));
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode POWER_EXPLANATION = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 15*60;
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "POWER_EXPLANATION");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("膝枕", "接受莉茜斯的提议，继续把头枕在她的腿上，同时告诉她你所看到的幻象。", POWER_EXPLANATION_CONTINUE) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "POWER_EXPLANATION_LAP"));
					}
				};
				
			} else if(index==2) {
				return new Response("站起来", "你觉得这样很不自在。站起来，告诉她你看到的幻象。", POWER_EXPLANATION_CONTINUE) { // But why
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "POWER_EXPLANATION_STAND_UP"));
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode POWER_EXPLANATION_CONTINUE = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 60;
		}
		
		@Override
		public String getContent() {
			return "";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("走出去", "走出莉茜斯的办公室，这样她就能去叫[siren.name]了。", END_SIREN) {
					@Override
					public void effects() {
						Main.game.getPlayer().setLocation(WorldType.LYSSIETH_PALACE, PlaceType.LYSSIETH_PALACE_SIREN_OFFICE);
						Main.game.getNpc(DarkSiren.class).setLocation(WorldType.LYSSIETH_PALACE, PlaceType.LYSSIETH_PALACE_SIREN_OFFICE);
						if(Main.game.getNpc(DarkSiren.class).getAffection(Main.game.getPlayer())<0) {
							Main.game.getTextEndStringBuilder().append(Main.game.getNpc(DarkSiren.class).setAffection(Main.game.getPlayer(), 0));
						}
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode END_SIREN = new DialogueNode("", "", true) {

		@Override
		public int getSecondsPassed() {
			return 15*60;
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "END_SIREN");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("地图", "收下她递给你的世界地图。", END_FINAL) {
					@Override
					public void effects() {
						Main.game.getPlayer().setNearestLocation(WorldType.LYSSIETH_PALACE, PlaceType.LYSSIETH_PALACE_CORRIDOR, false);
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.MAIN, Quest.MAIN_3_ELIS));
						Main.game.getTextEndStringBuilder().append(
								"<div class='container-full-width' style='text-align:center;'>"
										+ "[style.colourExcellent(你已解锁世界地图！)]<br/>"
										+ "<i>你可以通过手机的地图菜单查看，或者前往多米尼恩的任一出口地块并进入‘世界旅行’菜单来访问它。</i>"
								+ "</div>");
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode END_FINAL = new DialogueNode("", "", false) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("quests/main/lyssiethReveal", "END_FINAL");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return PlaceType.LYSSIETH_PALACE_CORRIDOR.getDialogue(false).getResponse(responseTab, index);
		}
	};
}

