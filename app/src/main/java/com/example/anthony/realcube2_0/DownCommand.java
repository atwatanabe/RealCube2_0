package com.example.anthony.realcube2_0;

public class DownCommand implements Command
{
    private Cube3x3 cube;
    private boolean inverted;

    public DownCommand(Cube3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.down(inverted);
    }

    public void unexecute()
    {
        cube.down(!inverted);
    }

    @Override
    public String toString()
    {
        return "Down " + inverted;
    }
}
