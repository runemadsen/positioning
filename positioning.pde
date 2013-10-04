import toxi.util.datatypes.*;
import toxi.color.*;
import geomerative.*;

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

void setup()
{
  size(1200, 800);
  colorMode(HSB, 1, 1, 1, 1);
  smooth();
  noStroke();
  RG.init(this);

  // test colors
  colors.add( TColor.newHSV(0, 1, 0.3) );
  colors.add( TColor.newHSV(0, 0.92, 0.51) );
  colors.add( TColor.newHSV(0, 0.96, 0.85) );
  colors.add( TColor.newHSV(0.24, 0.84, 0.2) );
  colors.add( TColor.newHSV(0.24, 0.79, 0.38) );
  colors.add( TColor.newHSV(0.27, 0.61, 0.64) );
  colors.add( TColor.newHSV(0.57, 0.28, 0.48) );
  colors.add( TColor.newHSV(0.57, 0.18, 0.59) );
  colors.add( TColor.newHSV(0.56, 0.13, 0.82) );
  colors.add( TColor.newHSV(0.12, 0.94, 0.59) );
  colors.add( TColor.newHSV(0.12, 0.91, 0.89) );
  colors.add( TColor.newHSV(0.16, 0.83, 0.99) );

  createNew();
}

/* Draw
--------------------------------------------------------- */

void draw()
{
  background(1);
  translate((width/2) - (theShape.getWidth()/2), (height/2) - (theShape.getHeight()/2));
  theShape.draw();
}

/* Create New
--------------------------------------------------------- */

void createNew()
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

void chooseNumShapes()
{
  float choose = random(1);

  if(choose < 0.3)
    numShapes = 1;
  else if(choose < 0.6)
    numShapes = round(random(2, 5));
  else
    numShapes = round(random(6, 20));
}

/* Choose: Shape Size
--------------------------------------------------------- */

void chooseShapeSize()
{
  shapeSize = round(random(40, 500));
}

/* Choose: Shape Type
--------------------------------------------------------- */

void chooseShapeType()
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

void choosePosition()
{
  WeightedRandomSet<Integer> positions = new WeightedRandomSet<Integer>();
  positions.add(HORIZONTAL, 1);
  positions.add(GRID, 1);
  positions.add(CENTER, 1);
  positions.add(ROTATION, 1);
  positionType = positions.getRandom();
}

void generateShapes()
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

RShape getShapeType(int type)
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

void keyPressed()
{
  if(key == 'r')
  {
    createNew();
  }
}