import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.dxf.*; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class StaggeredLayout extends PApplet {





stagger create;
boolean recordDXF = false, recordPDF = false, displayLayout = false;
RawDXF dxf;
ControlP5 cp5;

labels l;
clear clear;
textBoxes t;
buttons b;
grid g;

float margin, fontSize, step;
int cavs = 2;
PFont font, font2;

public void setup() {
  
  margin = 12;
  step = 85;
  font = createFont("arial", 20);
  font2 = createFont("arial", 16);

  cp5 = new ControlP5(this);
  l = new labels(cp5);
  t = new textBoxes(cp5);
  b = new buttons(cp5);
  clear = new clear();
}

public void draw() {
  g = new grid();
  assignValues();
  if (recordDXF) {
    // export staggered layout without dimensions as a DXF
    dxf = (RawDXF) createGraphics(width, height, DXF, month() + "-" + day() + "-" + year() + "_staggeredDie.dxf");
    beginRaw(dxf);
  }
  if (recordPDF) {
    // export layout with dimensions as a PDF
    beginRecord(PDF, month() + "-" + day() + "-" + year() + "_staggeredDie.pdf");
  } 
  if (displayLayout) {
    create.layout();
     if (recordDXF) {
      endRaw();
      recordDXF = false;
      dxf = null;
    }
    create.advParts();
    create.dimensions();
    if (recordPDF) {
      endRecord();
      recordPDF = false;
    }
  }
}

public void controlEvent(CallbackEvent theEvent) {
  if (theEvent.getController().equals(b.clearButton) &&
    theEvent.getAction() == ControlP5.ACTION_PRESS) {
    clear.values();
  } else if (theEvent.getController().equals(b.upButton) &&
    theEvent.getAction() == ControlP5.ACTION_PRESS) {
    b.up();
  } else if (theEvent.getController().equals(b.downButton) &&
    theEvent.getAction() == ControlP5.ACTION_PRESS) {
    b.down();
  } else if (theEvent.getController().getName() == "t.matWidth") {
      if (t.pDiameter.getText() == "") {
        l.numberOfSlits.setText("1");
        l.slitWidth.setText(t.matWidth.getText());
      }
  } else if (theEvent.getController().equals(b.exportButton) &&
             theEvent.getAction() == ControlP5.ACTION_PRESS) {
    b.export();
  } else if (theEvent.getController().equals(b.pdfButton) &&
             theEvent.getAction() == ControlP5.ACTION_PRESS) {
    b.pdf();
  } else if (theEvent.getController().equals(b.layoutButton) &&
             theEvent.getAction() == ControlP5.ACTION_PRESS) {
               displayLayout = true;
  }
}

public void assignValues() {
  float sP = PApplet.parseFloat(t.spc.getText());
  float pD = PApplet.parseFloat(t.pDiameter.getText());
  float aD = sP + pD;
  float aR = aD / 2;
  float aC = sqrt(sq(aD) - sq(aR));
  float dW = aC * (cavs - 1) + pD;
  float mW = PApplet.parseFloat(t.matWidth.getText());
  float nS = floor(mW / (dW + (2 * sP)));
  l.dieWidth.setText(str(dW));
  if (t.spc.getText() == "") {
    t.adv.setText("");
  } else {
    t.adv.setText(str(aD));
  }
  if (nS == 0) {
    l.numberOfSlits.setText("1");
    l.slitWidth.setText(t.matWidth.getText());
  } else {
    l.numberOfSlits.setText(str(nS));
    l.slitWidth.setText(str(mW / nS));
  }
  create = new stagger(pD, sP, PApplet.parseFloat(cavs), mW);
}
class buttons {
  
  float buttonHeight, buttonWidth;
  int bH, bW;
  Button layoutButton, exportButton, saveButton,
         upButton, downButton, clearButton, pdfButton;
  
  buttons(ControlP5 cp5) {
    buttonHeight = height - 7*step - 3*margin;
    buttonWidth = width/4 - 2*margin;
    bH = PApplet.parseInt(buttonHeight);
    bW = PApplet.parseInt(buttonWidth);
    
    
    layoutButton = cp5.addButton("Create Layout")
                      .setValue(0)
                      .setPosition(2*margin, 7*step + margin)
                      .setSize(bW,bH)
                      .setFont(font)
                      .setColorBackground(150);
                      
    upButton = cp5.addButton("+")
                  .setValue(0)
                  .setSize(20, 20)
                  .setPosition(width/4 - margin - 20, 5*step - 4*margin)
                  .setColorBackground(150);
                 
    downButton = cp5.addButton("-")
                  .setValue(0)
                  .setSize(20, 20)
                  .setPosition(width/4 - margin - 20, 5*step - 4*margin + 20)
                  .setColorBackground(150);
                  
    exportButton = cp5.addButton("Export DXF")
                      .setValue(0)
                      .setPosition(width/4 + 3*margin, height - 2*margin - 20)
                      .setSize(90, 20);
    pdfButton = cp5.addButton("Export PDF")
                   .setValue(0)
                   .setPosition(width/4 + 4*margin + 90, height - 2*margin - 20)
                   .setSize(90, 20);
                      
    saveButton = cp5.addButton("Save to Clipboard")
                    .setValue(0)
                    .setPosition(width/4 + 3*margin, height - 2*margin - 48)
                    .setSize(90, 20);
    clearButton = cp5.addButton("Clear Values")
                     .setValue(0)
                     .setSize(bW, 20)
                     .setPosition(2*margin, 7*step - 20);
  

  }
  
  public void up() {
    if (cavs < 30) {
      cavs+=1;
    }
    l.numberOnDie.setText(str(cavs));
  }
  
  public void down() {
    if (cavs > 2) {
      cavs-=1;
    }
    l.numberOnDie.setText(str(cavs));
  }
  
  public void export() {
    recordDXF = true;
  }
  
  public void pdf() {
    recordPDF = true;
  }
  
  public void layout() {
    create.layout();
  }
  
}
class clear {
  String val = "";
  
  clear() {
    
  }
  public void values() {
    t.matWidth.setText(val);
    t.pDiameter.setText(val);
    t.spc.setText(val);
    t.adv.setText(val);
    l.numberOnDie.setText("2");
    t.matWidth.setFocus(true);
  }
}
class grid {
  
  grid() {
    background(255);
    noFill();
    stroke(1);
    // left hand grid outline
    rect(margin, margin, width/4, height - 2*margin);
    //  right side grid outline
    rect(width/4 + 2*margin, margin, 3*width/4 - 3*margin, height - 2*margin);
    // on die number box
    rect(width/4 - margin - 60, 5*step - 4*margin, 40, 39);
  }
  
}
class labels{
  
  Textlabel matWLabel, pDiamLabel, spcLabel, 
            advLabel, onDieLabel, numSlitsLabel, 
            slitWidthLabel, numberOnDie, dieWidthLabel,
            numberOfSlits, slitWidth, dieWidth;
            
  float initial = step / 2;
  
  labels(ControlP5 cp5) {
// material width label      
      matWLabel = cp5.addTextlabel("materialWidthLabel")
                     .setPosition(2*margin, initial)
                     .setText("Material Width")
                     .setColorValue(0)
                     .setFont(font)
                     ;
// part diameter label                     
      pDiamLabel = cp5.addTextlabel("partDiameterLabel")
                      .setPosition(2*margin, initial + step)
                      .setText("Part Diameter")
                      .setColorValue(0)
                      .setFont(font)
                      ;
// die spacing label                      
      spcLabel = cp5.addTextlabel("spacingLabel")
                    .setPosition(2*margin, initial + 2*step)
                    .setText("Spacing")
                    .setColorValue(0)
                    .setFont(font)
                    ;
// value of advance label                    
      advLabel = cp5.addTextlabel("advanceLabel")
                    .setPosition(2*margin, initial + 3*step)
                    .setText("Advance")
                    .setColorValue(0)
                    .setFont(font)
                    ;
// number of cavities on the die label                    
      onDieLabel = cp5.addTextlabel("onDieLabel")
                      .setPosition(2*margin, initial + 4*step)
                      .setText("Cavities")
                      .setColorValue(0)
                      .setFont(font)
                      ;
      numberOnDie = cp5.addTextlabel("numberOnDie")
                       .setPosition(width/4 - margin - 50, 5*step - 3*margin - 5)
                       .setText(str(cavs))
                       .setColorValue(0)
                       .setFont(font)
                       ;
// number of material slits label                      
      numSlitsLabel = cp5.addTextlabel("numberOfSlitsLabel")
                         .setPosition(2*margin, initial + 4*step + 60)
                         .setText("Number of Slits")
                         .setColorValue(0)
                         .setFont(font2)
                         ;
      numberOfSlits = cp5.addTextlabel("numberOfSlits")
                         .setText("-")
                         .setFont(font2)
                         .setColorValue(0)
                         .setPosition(width/4 - 100, initial + 4*step + 60);
// slit width label                         
      slitWidthLabel = cp5.addTextlabel("slitWidthLabel")
                          .setPosition(2*margin, initial + 5*step + 25)
                          .setText("Slit Width")
                          .setColorValue(0)
                          .setFont(font2)
                          ;
      slitWidth = cp5.addTextlabel("slitWidth")
                     .setPosition(width/4 - 100, initial + 5*step + 25)
                     .setText("-")
                     .setColorValue(0)
                     .setFont(font2);
// die width label                         
      dieWidthLabel = cp5.addTextlabel("dieWidthLabel")
                          .setPosition(2*margin, initial + 5*step + 75)
                          .setText("Die Width")
                          .setColorValue(0)
                          .setFont(font2)
                          ;           
      dieWidth = cp5.addTextlabel("dieWidth")
                    .setPosition(width/4 - 100, initial + 5*step + 75)
                    .setText("-")
                    .setColorValue(0)
                    .setFont(font2);
  }
  
}
// 864 x 676 pixels to work with
// from vertex(324, 12) to vertex(1188, 688) plus an additional 12 pixel outer margin

class stagger {

  float diameter, advance, spacing, around, across, onDie, dieWidth, dieDepth, 
    newDiameter, newAdvance, newSpacing, newAround, newAcross, newWidth, 
    newDepth, centerX, centerY, firstLeft, firstRight, leftX, rightX, topY, 
    bottomY, row1, row2, matWidth, slits, slitWidth;

  PFont font3;

  stagger(float D, float S, float O, float M) {
    diameter = D;
    spacing = S;
    advance = diameter + spacing;
    onDie = O;
    around = advance / 2;
    across = sqrt(sq(advance) - sq(around));
    dieWidth = diameter + across*(onDie - 1);
    dieDepth = advance;
    leftX = 380;
    rightX = 1100;
    topY = 12;
    bottomY = 688;
    newDiameter = diameter * (rightX - leftX) / dieWidth;
    newSpacing = spacing * newDiameter / diameter;
    newAdvance = newDiameter + newSpacing;
    newAround = newAdvance / 2;
    newAcross = sqrt(sq(newAdvance) - sq(newAround));
    newWidth = newDiameter + newAcross*(onDie - 1);
    centerX = (rightX - leftX) / 2 + leftX;
    centerY = (bottomY- topY) / 2 + topY;
    row1 = centerY + (newAround / 2);
    row2 = row1 - newAround;
    firstLeft = centerX - (newWidth / 2) + (newDiameter / 2);
    firstRight = firstLeft + (newAcross * (onDie - 1));
    matWidth = M;
    slits = floor(matWidth / (dieWidth + 0.5f));
    slitWidth = matWidth / slits;
    font3 = createFont("arial", 14);
  }

  // this function will create the circle parts excluding the advanced parts in grey
  public void parts() {
    noFill();
    stroke(0);
    for (int i = 0; i < onDie; i++) {
      // if the number of cavities is even...
      if (i % 2 == 0) {
        stroke(0);
        ellipse(firstLeft + newAcross * i, row1, newDiameter, newDiameter);
        // if the number of cavities is odd...
      } else {
        stroke(0);
        ellipse(firstLeft + newAcross * i, row2, newDiameter, newDiameter);
      }
    }
  }

// this function will only create the advanced grey circle parts
  public void advParts() {
    noFill();
    stroke(160);
    int inc = PApplet.parseInt(radians(10));
    for (int i = 0; i < onDie; i++) {
      // if the number of cavities is even...
      if (i % 2 == 0) {
        noFill();
        stroke(160);
        ellipse(firstLeft + newAcross * i, row1 - newAdvance, newDiameter, newDiameter);
        //for (int a = 0; a < TWO_PI; a=+inc) {
        //  noStroke();
        //  fill(255);
        //  triangle(firstLeft + newAcross * i, row1 - newAdvance, firstLeft + newAcross * i + cos(a)*(newDiameter+5), row1 - newAdvance + sin(a)*(newDiameter+5), firstLeft + newAcross * i + cos(a+radians(5))*(newDiameter+5), row1 - newAdvance + sin(a+radians(5))*(newDiameter+5));
        //}
        
        // if the number of cavities is odd...
      } else {
        noFill();
        stroke(160);
        ellipse(firstLeft + newAcross * i, row2 - newAdvance, newDiameter, newDiameter);
      }
    }
  }
  
  // this function will create all of the dimensions including lines, text, and values
  public void dimensions() {
    float d = newDiameter/8;
    fill(0);
    stroke(0);
    textFont(font);

    // part diameter dimension
    float xO = firstLeft + newAcross + cos(radians(60))*(newDiameter / 2);
    float yO = row1 - newAdvance - newAround + sin(radians(60))*(newDiameter / 2);
    line(xO, yO, firstLeft + newAcross - cos(radians(60))*(newDiameter / 2), row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2));
    line(firstLeft + newAcross - cos(radians(60))*(newDiameter / 2), row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2), firstLeft, row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2));
    triangle(xO, yO, xO - cos(radians(45.7f)) * d, yO - sin(radians(45.7f)) * d, xO - cos(radians(74.3f))* d, yO - sin(radians(74.3f)) * d);
    xO = firstLeft + newAcross - cos(radians(60))*(newDiameter / 2);
    yO = row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2);
    triangle(xO, yO, xO + cos(radians(45.7f)) * d, yO + sin(radians(45.7f)) * d, xO + cos(radians(74.3f))* d, yO + sin(radians(74.3f)) * d);
    textAlign(LEFT);
    text(str(diameter) + "in. O.D.", firstLeft, row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2) - newSpacing / 4);

    // spacing dimension
    line(firstLeft + (newAcross / 2), row1 - (newAround / 2), firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - (newSpacing / 2));
    line(firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - (newSpacing / 2), firstLeft - (newDiameter / 2), row1 - (newDiameter / 2) - (newSpacing / 2));
    triangle(firstLeft + (newAcross / 2), row1 - (newAround / 2), firstLeft + (newAcross / 2) - cos(radians(45.7f))*d, row1 - (newAround / 2) - sin(radians(45.7f))*d, firstLeft + (newAcross / 2) - cos(radians(74.3f))*d, row1 - (newAround / 2) - sin(radians(74.3f))*d);
    textAlign(RIGHT);
    text(str(spacing) + "in. Spacing", firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - newSpacing);

    // die width dimension
    line(firstLeft - (newDiameter / 2), row1 + newSpacing, firstLeft - (newDiameter / 2), row1 + (newDiameter));
    if (onDie % 2 == 0) {
      line(firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row2 + newSpacing, firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newDiameter);
    } else {
      line(firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newSpacing, firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newDiameter);
    }
    line(firstLeft - (newDiameter / 2), row1 + newDiameter - newSpacing, firstRight + (newDiameter / 2), row1 + newDiameter - newSpacing);
    triangle(firstLeft - (newDiameter / 2) + 2, row1 + newDiameter - newSpacing, firstLeft - (newDiameter / 2) + newSpacing, row1 + newDiameter - (5*newSpacing / 4), firstLeft - (newDiameter / 2) + newSpacing, row1 + newDiameter - (3*newSpacing / 4));
    triangle(firstRight + (newDiameter / 2) - 2, row1 + newDiameter - newSpacing, firstRight + (newDiameter / 2) - newSpacing, row1 + newDiameter - (5*newSpacing / 4), firstRight + (newDiameter / 2) - newSpacing, row1 + newDiameter - (3*newSpacing / 4));
    textAlign(CENTER);
    text("Die Width:  " + str(dieWidth), centerX, row1 + newDiameter);

    // advance dimension
    if (onDie % 2 == 0) {
      stroke(0);
      line(firstRight, row2 + (newDiameter / 2), rightX + (newDiameter / 2), row2 + (newDiameter / 2));
      line(firstRight, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2), row2 + (newDiameter / 2) - newAdvance);
      line(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2), rightX + (newDiameter / 2) - newSpacing, row2 + newSpacing);
      line(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - newSpacing, row2 - (3*newSpacing/2));
      triangle(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2), rightX + (newDiameter / 2) - (5* newSpacing  / 4), row2 + (newDiameter / 2) - newSpacing, rightX + (newDiameter / 2) - (3*newSpacing / 4), row2 + (newDiameter / 2) - newSpacing);
      triangle(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - (5* newSpacing  / 4), row2 + (newDiameter / 2) + newSpacing - newAdvance, rightX + (newDiameter / 2) - (3*newSpacing / 4), row2 + (newDiameter / 2) + newSpacing - newAdvance);
      textAlign(CENTER);
      text("Advance", rightX + (newDiameter / 2) - newSpacing, row2 + (newSpacing / 2));
      text(str(advance)+"in", rightX + (newDiameter / 2) - newSpacing, row2 - (newSpacing / 2));
    } else {
      stroke(0);
      line(firstRight, row1 + (newDiameter / 2), rightX + (newDiameter / 2), row1 + (newDiameter / 2));
      line(firstRight, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2), row1 + (newDiameter / 2) - newAdvance);
      line(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2), rightX + (newDiameter / 2) - newSpacing, row1 + newSpacing);
      line(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - newSpacing, row1 - (3*newSpacing/2));
      triangle(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2), rightX + (newDiameter / 2) - (5* newSpacing  / 4), row1 + (newDiameter / 2) - newSpacing, rightX + (newDiameter / 2) - (3*newSpacing / 4), row1 + (newDiameter / 2) - newSpacing);
      triangle(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - (5* newSpacing  / 4), row1 + (newDiameter / 2) + newSpacing - newAdvance, rightX + (newDiameter / 2) - (3*newSpacing / 4), row1 + (newDiameter / 2) + newSpacing - newAdvance);
      textAlign(CENTER);
      text("Advance", rightX + (newDiameter / 2) - newSpacing, row1 + (newSpacing / 2));
      text(str(advance)+"in", rightX + (newDiameter / 2) - newSpacing, row1 - (newSpacing / 2));
    }

    // material width dimensions
    line(firstLeft - (5 * newDiameter / 8), row1 - (newDiameter / 4), firstLeft - (5 * newDiameter / 8), row1 + (5 * newDiameter / 4));
    line(firstRight + (5 * newDiameter / 8), row1 - (newDiameter / 4), firstRight + (5 * newDiameter / 8), row1 + (5 * newDiameter / 4));
    line(firstLeft - (5 * newDiameter / 8), row1 + (7 * newDiameter / 6), firstRight + (5 * newDiameter / 8), row1 + (7 * newDiameter / 6));
    triangle(firstLeft - (5 * newDiameter / 8) + 2, row1 + (7 * newDiameter / 6), firstLeft - (newDiameter/2), row1 + (7 * newDiameter / 6) + (newDiameter / 32), firstLeft - (newDiameter / 2), row1 + (7 * newDiameter / 6) - (newDiameter / 32));
    triangle(firstRight + (5 * newDiameter / 8) - 2, row1 + (7 * newDiameter / 6), firstRight + (newDiameter/2), row1 + (7 * newDiameter / 6) + (newDiameter / 32), firstRight + (newDiameter / 2), row1 + (7 * newDiameter / 6) - (newDiameter / 32));
    textAlign(CENTER);
    text("Material Width:  " + str(slitWidth), centerX, row1 + (4 * newDiameter / 3));
  }

  public void layout() {
    dimensions();
    parts();
    advParts();
  }
}
class textBoxes {
  
  Textfield matWidth, pDiameter, spc, adv;
  int textBoxWidth, textBoxHeight;
  float originX, originY;
  
  textBoxes(ControlP5 cp5) {
    textBoxWidth = 100;
    textBoxHeight = 30;
    originX = width / 4 - 100 - margin;
    originY = margin + (step/2) - (textBoxHeight/2);
    
    matWidth = cp5.addTextfield("materialWidth")
              .setPosition(originX, originY)
              .setSize(textBoxWidth, textBoxHeight)
              .setFocus(true)
              .setColorBackground(color(255,255,255))
              .setColorForeground(255)
              .setColorValue(0)
              .setFont(font)
              .setAutoClear(false)
              .setInputFilter(2);
                
    pDiameter = cp5.addTextfield("partDiameter")
                   .setPosition(originX, originY + step)
                   .setSize(textBoxWidth, textBoxHeight)
                   .setColorBackground(color(255,255,255))
                   .setColorForeground(255)
                   .setColorValue(0)
                   .setFont(font)
                   .setAutoClear(false)
                   .setInputFilter(2);
                   
    spc = cp5.addTextfield("spacing")
             .setPosition(originX, originY + 2*step)
             .setSize(textBoxWidth, textBoxHeight)
             .setColorBackground(color(255,255,255))
             .setColorForeground(255)
             .setColorValue(0)
             .setFont(font)
             .setAutoClear(false)
             .setInputFilter(2);
             
    adv = cp5.addTextfield("advance")
             .setPosition(originX, originY + 3*step)
             .setSize(textBoxWidth, textBoxHeight)
             .setColorBackground(color(255,255,255))
             .setColorForeground(255)
             .setColorValue(0)
             .setFont(font)
             .setAutoClear(false)
             .setInputFilter(2);
  }

}
  public void settings() {  size(1200, 700, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "StaggeredLayout" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
