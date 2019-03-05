package com.example.anthony.realcube2_0;

public class EquatorCommand implements Command
{
    private Cube3x3 cube;
    private boolean inverted;

    public EquatorCommand(Cube3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.equator(inverted);
    }

    public void unexecute()
    {
        cube.equator(!inverted);
    }

    @Override
    public String toString()
    {
        return "Equator " + inverted;
    }
}
