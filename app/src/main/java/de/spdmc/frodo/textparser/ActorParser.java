package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

/**
 * Created by lars on 28.01.16.
 */
public class ActorParser extends Parser {

    public ActorParser(){
        super();
        this.pattern = new String[]{"schauspieler", "mag"};
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        if (inArr.length == 1){ // nur ja oder nein als Antwort
            if(inArr[0].equals("ja")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_REASK);
                return ic;
            }
            else if(inArr[0].equals("nein")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_DECLINED);
                return ic;
            }
        }
        else if(inArr.length <= 4){ // nur evtl. ja + [NAME]
            if(!inArr[0].equals("nein")){
                if (inArr[0].equals("ja")){
                    String name = "";
                    for(int i = 1; i < inArr.length; i++){
                        name += " " + inArr[i];
                    }
                    name = name.replaceFirst(" ", "");
                    ic.addData(name);
                }
                else {
                    String help = "";
                    for(String s : inArr){
                        help += " " + s;
                    }
                    help = help.replaceFirst(" ", "");
                    ic.addData(help);
                }
                // TODO ueberpruefe (mit TMDB Anfrage?) ob ic.getData().get(0) Schauspieler ist, sonst wieder loeschen
                ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_REPLY);
            }
        } else {
            //TODO
        }

        return ic;
    }

}
