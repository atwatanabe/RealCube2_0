package com.example.anthony.realcube2_0;

public class MiddleCommand implements Command
{
    private Cube3x3x3 cube;
    private boolean inverted;

    public MiddleCommand(Cube3x3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.middle(inverted);
    }

    public void unexecute()
    {
        cube.middle(!inverted);
    }

    @Override
    public String toString()
    {
        return "Middle " + inverted;
    }
}
