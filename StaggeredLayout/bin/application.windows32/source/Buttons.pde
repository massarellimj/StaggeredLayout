class buttons {
  
  float buttonHeight, buttonWidth;
  int bH, bW;
  Button layoutButton, exportButton, saveButton,
         upButton, downButton, clearButton, pdfButton;
  
  buttons(ControlP5 cp5) {
    buttonHeight = height - 7*step - 3*margin;
    buttonWidth = width/4 - 2*margin;
    bH = int(buttonHeight);
    bW = int(buttonWidth);
    
    
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
  
  void up() {
    if (cavs < 30) {
      cavs+=1;
    }
    l.numberOnDie.setText(str(cavs));
  }
  
  void down() {
    if (cavs > 2) {
      cavs-=1;
    }
    l.numberOnDie.setText(str(cavs));
  }
  
  void export() {
    recordDXF = true;
  }
  
  void pdf() {
    recordPDF = true;
  }
  
  void layout() {
    create.layout();
  }
  
}