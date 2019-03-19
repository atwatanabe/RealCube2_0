package com.example.anthony.realcube2_0;

public class StandingCommand implements Command
{
    private Cube3x3x3 cube;
    private boolean inverted;

    public StandingCommand(Cube3x3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.standing(inverted);
    }

    public void unexecute()
    {
        cube.standing(!inverted);
    }

    @Override
    public String toString()
    {
        return "Standing " + inverted;
    }
}
