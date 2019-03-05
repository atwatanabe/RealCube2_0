package com.example.anthony.realcube2_0;

public class BackCommand implements Command
{
    private Cube3x3 cube;
    private boolean inverted;

    public BackCommand(Cube3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.back(inverted);
    }

    public void unexecute()
    {
        cube.back(!inverted);
    }

    @Override
    public String toString()
    {
        return "Back " + inverted;
    }
}
