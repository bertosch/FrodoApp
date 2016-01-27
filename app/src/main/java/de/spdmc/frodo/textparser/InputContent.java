package de.spdmc.frodo.textparser;

import java.util.ArrayList;
import java.util.Arrays;

import de.spdmc.frodo.enumerations.Enumerations;

public class InputContent {

    private Enumerations.DialogState dialogState;
    private ArrayList<String> data;

    public InputContent() {
        setDialogState(null);
        data = new ArrayList<String>();
    }

    public InputContent(Enumerations.DialogState at, ArrayList<String> data) {
        this.setDialogState(at);
        this.data = data;
    }

    public InputContent(Enumerations.DialogState at, String[] dataArr) {
        this.setDialogState(at);
        this.data = new ArrayList<String>(Arrays.asList(dataArr));
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void addData(String s) {
        this.data.add(s);
    }

    public Enumerations.DialogState getDialogState() {
        return dialogState;
    }

    public void setDialogState(Enumerations.DialogState dialogState) {
        this.dialogState = dialogState;
    }

}
