SCRIPT_NAME MAIN



DO_FADE 0 FADE_OUT

SET_TOTAL_NUMBER_OF_MISSIONS 0
SET_PROGRESS_TOTAL 0
SET_MISSION_RESPECT_TOTAL 0
SET_MAX_WANTED_LEVEL 6				    							 
SET_DEATHARREST_STATE OFF
SET_TIME_OF_DAY 08 00 


VAR_INT player1 scplayer player_group




REQUEST_COLLISION 21.0 -43.9 
LOAD_SCENE 21.0 -43.9 14.84 


CREATE_PLAYER 0 21.0 -43.9 14.84 player1

DISPLAY_TIMER_BARS FALSE


GET_PLAYER_GROUP player1 player_group
GET_PLAYER_CHAR player1 scplayer
SET_CAMERA_BEHIND_PLAYER
SET_CHAR_HEADING scplayer 180.0


LOAD_AND_LAUNCH_MISSION initial.sc
WAIT 0


// *************************************CONSTANTS*******************************************
//--- DATE REPORT CONSTS
CONST_INT DATE_IN_PROGRESS 		1
CONST_INT MEETING_IN_PROGRESS 	20




// *************************************VARIABLES*******************************************
VAR_FLOAT distance player_x player_y player_z
VAR_FLOAT heading 
VAR_FLOAT x y z
VAR_FLOAT on_footX on_footY	on_footZ
VAR_FLOAT in_carX in_carY in_carZ
VAR_INT iDateReport iAgentFlags iPhoneState	iCaller
VAR_INT gf_game_timer Players_Group
VAR_INT iTerminateAllAmbience iAreaCode iStoredAreaCode
iAreaCode = 0

// ***************************************MISSION VARS**********************************************
VAR_INT flag_player_on_mission
flag_player_on_mission = 0
DECLARE_MISSION_FLAG flag_player_on_mission



// Global variables for missions

DO_FADE 0 FADE_OUT
DISPLAY_ZONE_NAMES TRUE
WAIT 0
WAIT 0

SWITCH_WORLD_PROCESSING OFF //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
SET_FADING_COLOUR 0 0 0
WAIT 2000 //Wait until keys are initialised	//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
SWITCH_WORLD_PROCESSING ON //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

SET_MAX_WANTED_LEVEL 5	

IF IS_PLAYER_PLAYING player1
	GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 UPPR9B uppr9 0
	GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 PLAYER_FACE HEAD 1 
	GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 LOWR6C LOWR6 2 
	GIVE_PLAYER_CLOTHES_OUTSIDE_SHOP player1 FEET6A FEET6 3 
	BUILD_PLAYER_MODEL player1

	STORE_CLOTHES_STATE

	REQUEST_MODEL CAMERA 
	GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_CAMERA 10000
	ADD_SCORE player1 50000
	//Shut Player up (no cj sounds)
	SHUT_CHAR_UP scplayer true 

	SET_PLAYER_CONTROL player1 ON
	GET_PLAYER_GROUP Player1 Players_Group
	RELEASE_WEATHER
 ENDIF


//RESTART POSITIONS AFTER DEATH or BUSTED
ADD_HOSPITAL_RESTART (0.0 0.0 5.0) (0.0) 0 
ADD_POLICE_RESTART (0.0 0.0 5.0) (0.0) 0
WAIT 0

//CREATE_CAR_GENERATOR 0.0,0.0,0.0,0,-1,-1,-1,1,0,0,50,10000, cargen
wait 0
//LOAD_SCENE 21.0 -43.9 14.84 
DO_FADE 0 FADE_IN
//PRINT_BIG ( BHI ) 3000 2 
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

