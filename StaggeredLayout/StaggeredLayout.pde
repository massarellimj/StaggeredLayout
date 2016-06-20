import controlP5.*;
import processing.dxf.*;
import processing.pdf.*;

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

void setup() {
  size(1200, 700, P3D);
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

void draw() {
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

void controlEvent(CallbackEvent theEvent) {
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

void assignValues() {
  float sP = float(t.spc.getText());
  float pD = float(t.pDiameter.getText());
  float aD = sP + pD;
  float aR = aD / 2;
  float aC = sqrt(sq(aD) - sq(aR));
  float dW = aC * (cavs - 1) + pD;
  float mW = float(t.matWidth.getText());
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
  create = new stagger(pD, sP, float(cavs), mW);
}