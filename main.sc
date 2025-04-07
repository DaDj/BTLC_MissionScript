SCRIPT_NAME main

CONST_INT DATE_IN_PROGRESS 		1
CONST_INT MEETING_IN_PROGRESS 	20

VAR_INT player1 scplayer player_group
VAR_INT flag_player_on_mission

VAR_FLOAT distance player_x player_y player_z
VAR_FLOAT heading 
VAR_FLOAT x y z
VAR_FLOAT on_footX on_footY	on_footZ
VAR_FLOAT in_carX in_carY in_carZ
VAR_INT iDateReport iAgentFlags iPhoneState	iCaller
VAR_INT gf_game_timer Players_Group
VAR_INT iTerminateAllAmbience iAreaCode iStoredAreaCode
iAreaCode = 0
LOAD_SCENE 0.0 0.0 0.0


flag_player_on_mission = 0
DECLARE_MISSION_FLAG flag_player_on_mission

SET_DEATHARREST_STATE OFF
SET_TOTAL_NUMBER_OF_MISSIONS 0
SET_PROGRESS_TOTAL 0
SET_MISSION_RESPECT_TOTAL 0

//CREATE PLAYER
CREATE_PLAYER 0 0.0 0.0 5.0 player1
GET_PLAYER_CHAR player1 scplayer
GET_PLAYER_GROUP player1 player_group


GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 UPPR9B uppr9 0
GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 PLAYER_FACE HEAD 1 
GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 LOWR6C LOWR6 2 
GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 FEET6A FEET6 3 
BUILD_PLAYER_MODEL player1


REQUEST_MODEL CAMERA 
GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_CAMERA 10000
ADD_SCORE player1 50000


//RESTART POSITIONS AFTER DEATH or BUSTED
ADD_HOSPITAL_RESTART (0.0 0.0 5.0) (0.0) 0 
ADD_POLICE_RESTART (0.0 0.0 5.0) (0.0) 0

//Shut Player up (no cj sounds)
SHUT_CHAR_UP scplayer true 

SET_PLAYER_CONTROL player1 ON
GET_PLAYER_GROUP Player1 Players_Group
DO_FADE 500 FADE_IN

LOAD_AND_LAUNCH_MISSION initial.sc
wait 0


// *************************************VARIABLES*******************************************
//GENERAL
VAR_INT button_pressed controlmode mission_trigger_wait_time flag_cell_nation	game_timer


//Register Streamed Scripts
REGISTER_STREAMED_SCRIPT player_parachute.sc
REGISTER_STREAMED_SCRIPT camera.sc

// **************************************** OBJECT SCRIPTS ********************************************
REGISTER_STREAMED_SCRIPT gates_script.sc
REGISTER_STREAMED_SCRIPT food_vendor.sc


VAR_INT open_gate_now_flag
open_gate_now_flag = 1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT gates_script.sc bm_gate_15r 100 80.0 -1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT gates_script.sc ET_Gateslide01_d 100 80.0 -1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT gates_script.sc ET_Gateslide02_d 100 80.0 -1

ALLOCATE_STREAMED_SCRIPT_TO_OBJECT food_vendor.sc gb_hotdogstand01 100 70.0 -1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT food_vendor.sc gb_hotdogstand02 100 70.0 -1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT food_vendor.sc gb_hotdogstand03 100 70.0 -1
ALLOCATE_STREAMED_SCRIPT_TO_OBJECT food_vendor.sc gb_hotdogstand04 100 70.0 -1

VAR_INT number_of_instances_of_streamed_script
number_of_instances_of_streamed_script = 0

main_loop:
	wait 0
	//PARACHUTE
	IF HAS_CHAR_GOT_WEAPON scplayer WEAPONTYPE_PARACHUTE
		GET_NUMBER_OF_INSTANCES_OF_STREAMED_SCRIPT player_parachute.sc number_of_instances_of_streamed_script
		
		IF number_of_instances_of_streamed_script = 0
			STREAM_SCRIPT player_parachute.sc
			
			IF HAS_STREAMED_SCRIPT_LOADED player_parachute.sc
				START_NEW_STREAMED_SCRIPT player_parachute.sc
			ENDIF
		ENDIF
	ELSE
		MARK_STREAMED_SCRIPT_AS_NO_LONGER_NEEDED player_parachute.sc
	ENDIF

    // CAMERA
    IF HAS_CHAR_GOT_WEAPON scplayer WEAPONTYPE_CAMERA
    AND flag_player_on_mission = 0 
        GET_NUMBER_OF_INSTANCES_OF_STREAMED_SCRIPT camera.sc number_of_instances_of_streamed_script
        
        IF number_of_instances_of_streamed_script = 0
            STREAM_SCRIPT camera.sc
                    
            IF HAS_STREAMED_SCRIPT_LOADED camera.sc
            	START_NEW_STREAMED_SCRIPT camera.sc
            ENDIF
        ENDIF
    ELSE
    	MARK_STREAMED_SCRIPT_AS_NO_LONGER_NEEDED camera.sc
    ENDIF

GOTO main_loop

