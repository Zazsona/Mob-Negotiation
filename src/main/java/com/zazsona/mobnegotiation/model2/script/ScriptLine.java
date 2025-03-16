package com.zazsona.mobnegotiation.model2.script;

public class ScriptLine
{
    private String text;
    private ScriptLineType type;
    private ScriptLineTone tone;

    public ScriptLine()
    {
        this("");
    }

    public ScriptLine(String text)
    {
        this(text, ScriptLineType.DIALOGUE, ScriptLineTone.NEUTRAL);
    }

    public ScriptLine(String text, ScriptLineType type, ScriptLineTone tone) {
        this.text = text;
        this.type = type;
        this.tone = tone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ScriptLineType getType() {
        return type;
    }

    public void setType(ScriptLineType type) {
        this.type = type;
    }

    public ScriptLineTone getTone() {
        return tone;
    }

    public void setTone(ScriptLineTone tone) {
        this.tone = tone;
    }
}
