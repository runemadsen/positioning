import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import toxi.util.datatypes.*; 
import toxi.color.*; 
import geomerative.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class positioning extends PApplet {





/* Constants
--------------------------------------------------------- */

int HORIZONTAL = 0;
int GRID = 1;
int CENTER = 2;
int ROTATION = 3;

int TRIANGLE = 0;
int ELLIPSE = 1;
int RECTANGLE = 2;

/* Variables
--------------------------------------------------------- */

int shapeType;
int shapeSize;
int numShapes;
int positionType;

RShape theShape;

/* Test Stuff
--------------------------------------------------------- */

ColorList colors = new ColorList();

/* Setup
--------------------------------------------------------- */

public void setup()
{
  size(1200, 800);
  colorMode(HSB, 1, 1, 1, 1);
  smooth();
  noStroke();
  RG.init(this);

  // test colors
  colors.add( TColor.newHSV(0, 1, 0.3f) );
  colors.add( TColor.newHSV(0, 0.92f, 0.51f) );
  colors.add( TColor.newHSV(0, 0.96f, 0.85f) );
  colors.add( TColor.newHSV(0.24f, 0.84f, 0.2f) );
  colors.add( TColor.newHSV(0.24f, 0.79f, 0.38f) );
  colors.add( TColor.newHSV(0.27f, 0.61f, 0.64f) );
  colors.add( TColor.newHSV(0.57f, 0.28f, 0.48f) );
  colors.add( TColor.newHSV(0.57f, 0.18f, 0.59f) );
  colors.add( TColor.newHSV(0.56f, 0.13f, 0.82f) );
  colors.add( TColor.newHSV(0.12f, 0.94f, 0.59f) );
  colors.add( TColor.newHSV(0.12f, 0.91f, 0.89f) );
  colors.add( TColor.newHSV(0.16f, 0.83f, 0.99f) );

  createNew();
}

/* Draw
--------------------------------------------------------- */

public void draw()
{
  background(1);
  translate((width/2) - (theShape.getWidth()/2), (height/2) - (theShape.getHeight()/2));
  theShape.draw();
}

/* Create New
--------------------------------------------------------- */

public void createNew()
{
  theShape = new RShape();
  chooseNumShapes();
  chooseShapeSize();
  chooseShapeType();
  choosePosition();
  generateShapes();

  // center in middle of scene
}

/* Choose: Num Shapes
--------------------------------------------------------- */

public void chooseNumShapes()
{
  float choose = random(1);

  if(choose < 0.3f)
    numShapes = 1;
  else if(choose < 0.6f)
    numShapes = round(random(2, 5));
  else
    numShapes = round(random(6, 20));
}

/* Choose: Shape Size
--------------------------------------------------------- */

public void chooseShapeSize()
{
  shapeSize = round(random(40, 500));
}

/* Choose: Shape Type
--------------------------------------------------------- */

public void chooseShapeType()
{
  WeightedRandomSet<Integer> shapes = new WeightedRandomSet<Integer>();
  shapes.add(TRIANGLE, 1);
  shapes.add(RECTANGLE, 1);
  shapes.add(ELLIPSE, 1);

  // we could manipulate it here in size
  // we could add numerous shapes?

  shapeType = shapes.getRandom();
}

/* Choose: Position
--------------------------------------------------------- */

public void choosePosition()
{
  WeightedRandomSet<Integer> positions = new WeightedRandomSet<Integer>();
  positions.add(HORIZONTAL, 1);
  positions.add(GRID, 1);
  positions.add(CENTER, 1);
  positions.add(ROTATION, 1);
  positionType = positions.getRandom();
}

public void generateShapes()
{
  RShape newShape;

  positionType = GRID;

  // horizontal
  if(positionType == HORIZONTAL)
  {
    for(int x = 0; x < numShapes; x++)
    {
      newShape = getShapeType(shapeType);
      newShape.translate(shapeSize * x, 0);
      theShape.addChild(newShape);
    }
  }

  // grid
  else if(positionType == GRID)
  {
    for(int x = 0; x < numShapes; x++)
    {
      for(int y = 0; y < numShapes; y++)
      {
        newShape = getShapeType(shapeType);
        newShape.translate(shapeSize * x, shapeSize * y);
        theShape.addChild(newShape);
      }
    }
  }

  // center
  else if(positionType == CENTER)
  {

  }

  // rotation
  else if(positionType == ROTATION)
  {

  }

  // set colors
  for(int i = 0; i < theShape.children.length; i++)
  {
    TColor col = colors.get(i % colors.size());
    theShape.children[i].setFill(col.toARGB());
  }
}

public RShape getShapeType(int type)
{
  if(type == ELLIPSE)
  {
    return RShape.createCircle(0, 0, shapeSize);
  }
  else if(type == RECTANGLE)
  {
    return RShape.createRectangle(0, 0, shapeSize, shapeSize);
  }
  else { // THIS SHOULD BE A TRIANGLE
    return RShape.createCircle(0, 0, shapeSize);
  }
}

/* Events
--------------------------------------------------------- */

public void keyPressed()
{
  if(key == 'r')
  {
    createNew();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "positioning" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
