package com.example.anthony.realcube2_0;

import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cuboid extends TwistyPuzzle
{
    private int width;
    private int height;
    private int depth;
    private float sideLength;   //the length of the side of one tile (since not all
                                //sides of a cuboid are necessarily the same length
    private float spacing;
    private float[][] colors;

    private Piece[] corners;
    private Piece[][] xEdges;
    private Piece[][] yEdges;
    private Piece[][] zEdges;
    private Piece[][] frontCenters;
    private Piece[][] backCenters;
    private Piece[][] leftCenters;
    private Piece[][] rightCenters;
    private Piece[][] upCenters;
    private Piece[][] downCenters;


    public Cuboid(int w, int h, int d, float sl, float sp, float[][] mColors)
    {
        width = w;
        height = h;
        depth = d;
        sideLength = sl;
        spacing = sp;
        colors = mColors;
        init();
    }

    public Cuboid()
    {
        width = 3;
        height = 3;
        depth = 3;
        sideLength = 0.5f;
        spacing = 0.1f;
        float alpha = 1f;

        float[] white = {0xff / 256f, 0xff / 256f, 0xff / 256f, alpha};    //up
        float[] yellow = {0xff / 256f, 0xd5 / 256f, 0x00 / 256f, alpha};   //down
        float[] red = {0xc4 / 256f, 0x1e / 256f, 0x3a / 256f, alpha};      //left
        float[] orange = {0xff / 256f, 0x58 / 256f, 0x00 / 256f, alpha};   //right
        float[] blue = {0x00 / 256f, 0x51 / 256f, 0xba / 256f, alpha};     //front
        float[] green = {0x00 / 256f, 0x9e / 256f, 0x60 / 256f, alpha};    //back

        colors = new float[][] {white, yellow, red, orange, blue, green};
        init();
    }

    private void init()
    {
        pieces = new ArrayList<Piece>();

        //generate pieces of cuboid and add them to @pieces

        float unit = sideLength + spacing;
        float halfUnit = unit / 2f;
        float widthRadius = width * halfUnit;
        float heightRadius = height * halfUnit;
        float depthRadius = depth * halfUnit;

        float[] up = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Up, heightRadius);
        float[] down = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Down, heightRadius);
        float[] left = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Left, widthRadius);
        float[] right = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Right, widthRadius);
        float[] front = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Front, depthRadius);
        float[] back = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Back, depthRadius);

        int squareStride = Square.verticesPerSquare * Square.coordsPerVertex;
//        int centerStride = squareStride;
        int edgeStride = 2 * squareStride;
        int cornerStride = 3 * squareStride;

        int colorStride = 4;


//        int isSortaTall = height > 1 ? 1 : 0;
//        int isSortaWide = width > 1 ? 1 : 0;
//        int isSortaDeep = depth > 1 ? 1 : 0;
//
//        int numCorners = (int) Math.pow(2, isSortaWide + isSortaTall + isSortaDeep);

        //the 8 corners. If any of the dimensions is 1, some of the corners will overlap for now; will optimize later
        float[] ulf = new float[cornerStride];      float[][] ulfColors = new float[3][colorStride];
        float[] ulb = new float[cornerStride];      float[][] ulbColors = new float[3][colorStride];
        float[] urf = new float[cornerStride];      float[][] urfColors = new float[3][colorStride];
        float[] urb = new float[cornerStride];      float[][] urbColors = new float[3][colorStride];
        float[] dlf = new float[cornerStride];      float[][] dlfColors = new float[3][colorStride];
        float[] dlb = new float[cornerStride];      float[][] dlbColors = new float[3][colorStride];
        float[] drf = new float[cornerStride];      float[][] drfColors = new float[3][colorStride];
        float[] drb = new float[cornerStride];      float[][] drbColors = new float[3][colorStride];

        System.arraycopy(up, (depth - 1) * squareStride, ulf, 0, squareStride);
        System.arraycopy(left, (depth - 1) * height * squareStride, ulf, squareStride, squareStride);
        System.arraycopy(front, 0, ulf, 2 * squareStride, squareStride);

        System.arraycopy(up, 0, ulb, 0, squareStride);
        System.arraycopy(left, 0, ulb, squareStride, squareStride);
        System.arraycopy(back, (width - 1) * height * squareStride, ulb, 2 * squareStride, squareStride);

        System.arraycopy(up, up.length - squareStride, urf, 0, squareStride);
        System.arraycopy(right, 0, urf, squareStride, squareStride);
        System.arraycopy(front, (width - 1) * height * squareStride, urf, 2 * squareStride, squareStride);

        System.arraycopy(up, (width - 1) * depth * squareStride, urb, 0, squareStride);
        System.arraycopy(right, (depth - 1) * height * squareStride, urb, squareStride, squareStride);
        System.arraycopy(back, 0, urb, 2 * squareStride, squareStride);

        System.arraycopy(down, 0, dlf, 0, squareStride);
        System.arraycopy(left, left.length - squareStride, dlf, squareStride, squareStride);
        System.arraycopy(front, (height - 1) * squareStride, dlf, 2 * squareStride, squareStride);

        System.arraycopy(down, (depth - 1) * squareStride, dlb, 0, squareStride);
        System.arraycopy(left, (height - 1) * squareStride, dlb, squareStride, squareStride);
        System.arraycopy(back, back.length - squareStride, dlb, 2 * squareStride, squareStride);

        System.arraycopy(down, (width - 1) * depth * squareStride, drf, 0, squareStride);
        System.arraycopy(right, (height - 1) * squareStride, drf, squareStride, squareStride);
        System.arraycopy(front, front.length - squareStride, drf, 2 * squareStride, squareStride);

        System.arraycopy(down, down.length - squareStride, drb, 0, squareStride);
        System.arraycopy(right, right.length - squareStride, drb, squareStride, squareStride);
        System.arraycopy(back, (height - 1) * squareStride, drb, 2 * squareStride, squareStride);

        ulfColors[0] = colors[0];
        ulfColors[1] = colors[2];
        ulfColors[2] = colors[4];

        ulbColors[0] = colors[0];
        ulbColors[1] = colors[2];
        ulbColors[2] = colors[5];

        urfColors[0] = colors[0];
        urfColors[1] = colors[3];
        urfColors[2] = colors[4];

        urbColors[0] = colors[0];
        urbColors[1] = colors[3];
        urbColors[2] = colors[5];


        dlfColors[0] = colors[1];
        dlfColors[1] = colors[2];
        dlfColors[2] = colors[4];

        dlbColors[0] = colors[1];
        dlbColors[1] = colors[2];
        dlbColors[2] = colors[5];

        drfColors[0] = colors[1];
        drfColors[1] = colors[3];
        drfColors[2] = colors[4];

        drbColors[0] = colors[1];
        drbColors[1] = colors[3];
        drbColors[2] = colors[5];


        Piece ulfPiece = new PieceCuboidCorner(ulf, ulfColors);
        Piece ulbPiece = new PieceCuboidCorner(ulb, ulbColors);
        Piece urfPiece = new PieceCuboidCorner(urf, urfColors);
        Piece urbPiece = new PieceCuboidCorner(urb, urbColors);
        Piece dlfPiece = new PieceCuboidCorner(dlf, dlfColors);
        Piece dlbPiece = new PieceCuboidCorner(dlb, dlbColors);
        Piece drfPiece = new PieceCuboidCorner(drf, drfColors);
        Piece drbPiece = new PieceCuboidCorner(drb, drbColors);

//        pieces.add(ulfPiece);
//        pieces.add(ulbPiece);
//        pieces.add(urfPiece);
//        pieces.add(urbPiece);
//        pieces.add(dlfPiece);
//        pieces.add(dlbPiece);
//        pieces.add(drfPiece);
//        pieces.add(drbPiece);

        corners = new PieceCuboidCorner[8];
        corners[0] = ulfPiece;
        corners[1] = ulbPiece;
        corners[2] = urfPiece;
        corners[3] = urbPiece;
        corners[4] = dlfPiece;
        corners[5] = dlbPiece;
        corners[6] = drfPiece;
        corners[7] = drbPiece;

        pieces.addAll(Arrays.asList(corners));

        boolean isTall = height > 2;
        boolean isWide = width > 2;
        boolean isDeep = depth > 2;

        //create edge pieces
        if (isTall)
        {
            float[][] lf = new float[height - 2][edgeStride];   float[][] lfColors = new float[2][colorStride];
            float[][] lb = new float[height - 2][edgeStride];   float[][] lbColors = new float[2][colorStride];
            float[][] rf = new float[height - 2][edgeStride];   float[][] rfColors = new float[2][colorStride];
            float[][] rb = new float[height - 2][edgeStride];   float[][] rbColors = new float[2][colorStride];

            lfColors[0] = colors[2];
            lfColors[1] = colors[4];
            lbColors[0] = colors[2];
            lbColors[1] = colors[5];
            rfColors[0] = colors[3];
            rfColors[1] = colors[4];
            rbColors[0] = colors[3];
            rbColors[1] = colors[5];

            yEdges = new PieceCuboidEdge[4][height - 2];

            for (int y = 1; y < height - 1; ++y)
            {
                System.arraycopy(left, (depth - 1) * height * squareStride + y * squareStride, lf[y - 1], 0, squareStride);
                System.arraycopy(front, y * squareStride, lf[y - 1], squareStride, squareStride);

                System.arraycopy(left, y * squareStride, lb[y - 1],  0, squareStride);
                System.arraycopy(back, (width - 1) * height * squareStride + y * squareStride, lb[y - 1], squareStride, squareStride);

                System.arraycopy(right, y * squareStride, rf[y - 1], 0, squareStride);
                System.arraycopy(front, (width - 1) * height * squareStride + y * squareStride, rf[y - 1], squareStride, squareStride);

                System.arraycopy(right, (depth - 1) * height * squareStride + y * squareStride, rb[y - 1], 0, squareStride);
                System.arraycopy(back, y * squareStride, rb[y - 1], squareStride, squareStride);

                Piece lfEdge = new PieceCuboidEdge(lf[y - 1], lfColors);
                Piece lbEdge = new PieceCuboidEdge(lb[y - 1], lbColors);
                Piece rfEdge = new PieceCuboidEdge(rf[y - 1], rfColors);
                Piece rbEdge = new PieceCuboidEdge(rb[y - 1], rbColors);

                pieces.add(lfEdge);
                pieces.add(lbEdge);
                pieces.add(rfEdge);
                pieces.add(rbEdge);

                yEdges[0][y - 1] = lfEdge;
                yEdges[1][y - 1] = lbEdge;
                yEdges[2][y - 1] = rbEdge;
                yEdges[3][y - 1] = rfEdge;

            }

//            for (int i = 0; i < yEdges.length; ++i)
//            {
//                pieces.addAll(Arrays.asList(yEdges[i]));
//            }

        }

        if (isWide)
        {
            float[][] uf = new float[width - 2][edgeStride];    float[][] ufColors = new float[2][colorStride];
            float[][] ub = new float[width - 2][edgeStride];    float[][] ubColors = new float[2][colorStride];
            float[][] df = new float[width - 2][edgeStride];    float[][] dfColors = new float[2][colorStride];
            float[][] db = new float[width - 2][edgeStride];    float[][] dbColors = new float[2][colorStride];

            ufColors[0] = colors[0];
            ufColors[1] = colors[4];
            ubColors[0] = colors[0];
            ubColors[1] = colors[5];
            dfColors[0] = colors[1];
            dfColors[1] = colors[4];
            dbColors[0] = colors[1];
            dbColors[1] = colors[5];

            xEdges = new PieceCuboidEdge[4][width - 2];

            for (int x = 1; x < width - 1; ++x)
            {
                System.arraycopy(up, ((1 + x) * depth - 1) * squareStride, uf[x - 1], 0, squareStride);
                System.arraycopy(front, height * x * squareStride, uf[x - 1], squareStride, squareStride);

                System.arraycopy(up, depth * x * squareStride, ub[x - 1], 0, squareStride);
                System.arraycopy(back, back.length - (1 + x) * height * squareStride, ub[x - 1], squareStride, squareStride);

                System.arraycopy(down, x * depth * squareStride, df[x - 1], 0, squareStride);
                System.arraycopy(front, ((1 + x) * height - 1) * squareStride, df[x - 1], squareStride, squareStride);

                System.arraycopy(down, ((1 + x) * depth - 1) * squareStride, db[x - 1], 0, squareStride);
                System.arraycopy(back, back.length - squareStride - x * height * squareStride, db[x - 1], squareStride, squareStride);

                Piece ufEdge = new PieceCuboidEdge(uf[x - 1], ufColors);
                Piece ubEdge = new PieceCuboidEdge(ub[x - 1], ubColors);
                Piece dfEdge = new PieceCuboidEdge(df[x - 1], dfColors);
                Piece dbEdge = new PieceCuboidEdge(db[x - 1], dbColors);

                pieces.add(ufEdge);
                pieces.add(ubEdge);
                pieces.add(dfEdge);
                pieces.add(dbEdge);

                xEdges[0][x - 1] = ufEdge;
                xEdges[1][x - 1] = ubEdge;
                xEdges[2][x - 1] = dbEdge;
                xEdges[3][x - 1] = dfEdge;
            }

//            for (int i = 0; i < xEdges.length; ++i)
//            {
//                pieces.addAll(Arrays.asList(xEdges[i]));
//            }
        }

        if (isDeep)
        {
            float[][] ul = new float[depth - 2][edgeStride];    float[][] ulColors = new float[2][colorStride];
            float[][] ur = new float[depth - 2][edgeStride];    float[][] urColors = new float[2][colorStride];
            float[][] dl = new float[depth - 2][edgeStride];    float[][] dlColors = new float[2][colorStride];
            float[][] dr = new float[depth - 2][edgeStride];    float[][] drColors = new float[2][colorStride];

            ulColors[0] = colors[0];
            ulColors[1] = colors[2];
            urColors[0] = colors[0];
            urColors[1] = colors[3];
            dlColors[0] = colors[1];
            dlColors[1] = colors[2];
            drColors[0] = colors[1];
            drColors[1] = colors[3];

            zEdges = new PieceCuboidEdge[4][depth - 2];

            for (int z = 1; z < depth - 1; ++z)
            {
                System.arraycopy(up, z * squareStride, ul[z - 1], 0, squareStride);
                System.arraycopy(left, z * height * squareStride, ul[z - 1], squareStride, squareStride);

                System.arraycopy(up, (width - 1) * depth * squareStride + z * squareStride, ur[z - 1], 0, squareStride);
                System.arraycopy(right, (depth - 1) * height * squareStride - z * height * squareStride, ur[z - 1], squareStride, squareStride);

                System.arraycopy(down, z * squareStride, dl[z - 1], 0, squareStride);
                System.arraycopy(left, left.length - squareStride - z * height * squareStride, dl[z - 1], squareStride, squareStride);

                System.arraycopy(down, (width - 1) * depth * squareStride + z * squareStride, dr[z - 1], 0, squareStride);
                System.arraycopy(right, (height - 1) * squareStride + z * height * squareStride, dr[z - 1], squareStride, squareStride);

                Piece ulEdge = new PieceCuboidEdge(ul[z - 1], ulColors);
                Piece urEdge = new PieceCuboidEdge(ur[z - 1], urColors);
                Piece dlEdge = new PieceCuboidEdge(dl[z - 1], dlColors);
                Piece drEdge = new PieceCuboidEdge(dr[z - 1], drColors);

                pieces.add(ulEdge);
                pieces.add(urEdge);
                pieces.add(dlEdge);
                pieces.add(drEdge);

                zEdges[0][z - 1] = ulEdge;
                zEdges[1][z - 1] = urEdge;
                zEdges[2][z - 1] = drEdge;
                zEdges[3][z - 1] = dlEdge;
            }

//            for (int i = 0; i < depth - 2; ++i)
//            {
//                pieces.addAll(Arrays.asList(zEdges[i]));
//            }
        }

        //create centers

        //up and down
        if (isWide && isDeep)
        {
            upCenters = new PieceCuboidCenter[width - 2][depth - 2];
            downCenters = new PieceCuboidCenter[width - 2][depth - 2];

            for (int x = 1; x < width - 1; ++x)
            {
                for (int z = 1; z < depth - 1; ++z)
                {
                    float[] u = new float[squareStride];
                    float[] d = new float[squareStride];

                    int index = x * depth * squareStride + z * squareStride;
                    System.arraycopy(up, index, u, 0, squareStride);
                    System.arraycopy(down, index, d, 0, squareStride);

                    Piece uCenter = new PieceCuboidCenter(u, colors[0]);
                    Piece dCenter = new PieceCuboidCenter(d, colors[1]);

                    pieces.add(uCenter);
                    pieces.add(dCenter);

                    upCenters[x - 1][z - 1] = uCenter;
                    downCenters[x - 1][z - 1] = dCenter;
                }
            }
        }

        //left and right
        if (isTall && isDeep)
        {
            leftCenters = new PieceCuboidCenter[height - 2][depth - 2];
            rightCenters = new PieceCuboidCenter[height - 2][depth - 2];

            for (int y = 1; y < height - 1; ++y)
            {
                for (int z = 1; z < depth - 1; ++z)
                {
                    float[] l = new float[squareStride];
                    float[] r = new float[squareStride];

                    int index = z * height * squareStride + y * squareStride;
                    System.arraycopy(left, index, l, 0, squareStride);
                    System.arraycopy(right, index, r, 0, squareStride);

                    Piece lCenter = new PieceCuboidCenter(l, colors[2]);
                    Piece rCenter = new PieceCuboidCenter(r, colors[3]);

                    pieces.add(lCenter);
                    pieces.add(rCenter);

                    leftCenters[y - 1][z - 1] = lCenter;
                    rightCenters[y - 1][z - 1] = rCenter;
                }
            }
        }

        //front and back
        if (isWide && isTall)
        {
            frontCenters = new PieceCuboidCenter[width - 2][height - 2];
            backCenters = new PieceCuboidCenter[width - 2][height - 2];

            for (int x = 1; x < width - 1; ++x)
            {
                for (int y = 1; y < height - 1; ++y)
                {
                    float[] f = new float[squareStride];
                    float[] b = new float[squareStride];

                    int index = x * height * squareStride + y * squareStride;
                    System.arraycopy(front, index, f, 0, squareStride);
                    System.arraycopy(back, index, b, 0, squareStride);

                    Piece fCenter = new PieceCuboidCenter(f, colors[4]);
                    Piece bCenter = new PieceCuboidCenter(b, colors[5]);

                    pieces.add(fCenter);
                    pieces.add(bCenter);

                    frontCenters[x - 1][y - 1] = fCenter;
                    backCenters[x - 1][y - 1] = bCenter;
                }
            }
        }
    }


    public List<Piece> getPiecesInSlice(int axis, int layer)
    {
        List<Piece> result = new ArrayList<Piece>();

        switch (axis)
        {
            case 0:     //up/down/y
            {
                if (layer == 0)
                {
                    for (int i = 0; i < corners.length / 2; ++i)
                    {
                        result.add(corners[i]);                  //up corners
                    }
                    result.addAll(Arrays.asList(xEdges[0]));     //front edges
                    result.addAll(Arrays.asList(xEdges[1]));     //back edges
                    result.addAll(Arrays.asList(zEdges[0]));     //left edges
                    result.addAll(Arrays.asList(zEdges[1]));     //right edges
                    for (Piece[] p : upCenters)
                    {
                        result.addAll(Arrays.asList(p));        //up centers
                    }
                    break;
                }
                else if (layer == height - 1)
                {
                    for (int i = corners.length / 2; i < corners.length; ++i)
                    {
                        result.add(corners[i]);                 //down corners
                    }
                    result.addAll(Arrays.asList(xEdges[2]));    //back edges
                    result.addAll(Arrays.asList(xEdges[3]));    //front edges
                    result.addAll(Arrays.asList(zEdges[2]));    //right edges
                    result.addAll(Arrays.asList(zEdges[3]));    //left edges
                    for (Piece[] p : downCenters)
                    {
                        result.addAll(Arrays.asList(p));        //down centers
                    }
                    break;
                }
                else if (layer < 0 || layer >= height)
                {
                    Log.i("getPieces", "invalid layer " + layer + " " + height);
                    break;
                }
                else
                {
                    for (Piece[] p : yEdges)
                    {
                        result.add(p[layer - 1]);                   //edges
                    }
                    result.addAll(Arrays.asList(frontCenters[layer]));      //front centers
                    result.addAll(Arrays.asList(backCenters[layer]));       //back centers
                    result.addAll(Arrays.asList(leftCenters[layer]));       //left centers
                    result.addAll(Arrays.asList(rightCenters[layer]));      //right centers
                    break;
                }
            }
            case 1:     //left/right/x
            {
                if (layer == 0)
                {
                    result.add(corners[0]);     //ulf
                    result.add(corners[1]);     //ulb
                    result.add(corners[4]);     //dlf
                    result.add(corners[5]);     //dlb
                    result.addAll(Arrays.asList(zEdges[0]));    //up edges
                    result.addAll(Arrays.asList(zEdges[3]));    //down edges
                    result.addAll(Arrays.asList(yEdges[0]));    //front edges
                    result.addAll(Arrays.asList(yEdges[1]));    //back edges
                    for (int i = 0; i < leftCenters.length; ++i)
                    {
                        result.addAll(Arrays.asList(leftCenters[i]));   //left centers
                    }
                    break;
                }
                else if (layer == width - 1)
                {
                    result.add(corners[2]);     //urf
                    result.add(corners[3]);     //urb
                    result.add(corners[6]);     //drf
                    result.add(corners[7]);     //drb
                    result.addAll(Arrays.asList(zEdges[1]));    //up edges
                    result.addAll(Arrays.asList(zEdges[2]));    //down edges
                    result.addAll(Arrays.asList(yEdges[2]));    //front edges
                    result.addAll(Arrays.asList(yEdges[3]));    //back edges
                    for (int i = 0; i < rightCenters.length; ++i)
                    {
                        result.addAll(Arrays.asList(rightCenters[i]));  //right centers
                    }
                    break;
                }
                else if (layer < 0 || layer >= width)
                {
                    Log.i("getPieces", "invalid layer " + layer + " " + width);
                    break;
                }
                else
                {
                    for (Piece[] p : xEdges)
                    {
                        result.add(p[layer - 1]);           //edges
                    }
                    for (int i = 0; i < upCenters.length; ++i)
                    {
                        result.add(upCenters[i][layer - 1]);
                        result.add(downCenters[i][layer - 1]);
                    }
                    for (int i = 0; i < frontCenters.length; ++i)
                    {
                        result.add(frontCenters[i][layer - 1]);
                        result.add(backCenters[i][backCenters[i].length - layer]);
                    }
                    break;
                }
            }
            case 2:     //front/back/z
            {
                if (layer == 0)
                {
                    for (int i = 0; i < corners.length; i += 2)
                    {
                        result.add(corners[i]);     //front corners
                    }
                    result.addAll(Arrays.asList(xEdges[0]));    //up edges
                    result.addAll(Arrays.asList(xEdges[3]));    //down edges
                    result.addAll(Arrays.asList(yEdges[0]));    //left edges
                    result.addAll(Arrays.asList(yEdges[3]));    //right edges
                    for (Piece[] p : frontCenters)
                    {
                        result.addAll(Arrays.asList(p));        //front centers
                    }
                    break;
                }
                else if (layer == depth - 1)
                {
                    for (int i = 1; i < corners.length; i += 2)
                    {
                        result.add(corners[i]);
                    }
                    result.addAll(Arrays.asList(xEdges[1]));    //up edges
                    result.addAll(Arrays.asList(xEdges[2]));    //down edges
                    result.addAll(Arrays.asList(yEdges[1]));    //left edges
                    result.addAll(Arrays.asList(yEdges[2]));    //right edges
                    for (Piece[] p : backCenters)
                    {
                        result.addAll(Arrays.asList(p));        //back centers
                    }
                    break;
                }
                else if (layer < 0 || layer >= depth)
                {
                    Log.i("getPieces", "invalid layer " + layer + " " + depth);
                    break;
                }
                else
                {
                    for (Piece[] p : zEdges)
                    {
                        result.add(p[layer - 1]);       //edges
                    }
                    result.addAll(Arrays.asList(upCenters[upCenters.length - layer]));      //up centers
                    result.addAll(Arrays.asList(downCenters[layer - 1]));                   //down centers
                    for (Piece[] p : leftCenters)
                    {
                        result.add(p[p.length - layer]);        //left centers
                    }
                    for (Piece[] p : rightCenters)
                    {
                        result.add(p[layer - 1]);               //right cetners
                    }
                    break;
                }
            }
            default:
            {
                Log.i("getPieces", "invalid axis number");
            }
        }

        return result;
    }

    public void setAngleTest(int ang)
    {
        List<Piece> temp = getPiecesInSlice(0, 0);
        int numFloats = 0;
        for (Piece p : temp)
        {
            numFloats += p.getNumCoords();
        }
        List<Float> buffer = new ArrayList<Float>(numFloats);
        for (Piece p : temp)
        {
            for (float f : p.getCoords())
            {
                buffer.add(f);
            }

        }
    }
}
