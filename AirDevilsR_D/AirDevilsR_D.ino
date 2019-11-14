#include<Wire.h>
#include <SPI.h>
#include <SD.h>

const String FILE_NAME = "inc.txt"; //file with next file name (unique naming per file)
const int MPU_addr=0x68;

File myFile;
String fileNm = "";
const int INTERVAL = 10; //interval to read at (milliseconds) set at 10 for 100hz  
unsigned long start;
int num;
int pitot, thrust;
float AcX, AcY, AcZ;
const float constant = 1705.26; //1620 divided by 9.5

void setup() {
  //INITIALIZE ACCELEROMETER
  Wire.begin();
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
  Wire.endTransmission(true);
  
  Serial.begin(115200);

  //Wait for Serial PORT to open
  //while (!Serial) { }

  //INITIALIZE SD CARD
  Serial.print("Initializing SD card...");
  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
    return;
  }

  //READING FILE_NAME to name current file
  myFile = SD.open(FILE_NAME);
  if(myFile) {
    while (myFile.available()) {
      num = myFile.parseInt();
      Serial.println(num);
    }
    myFile.close();
  }else{
    Serial.println("error opening");
  }

  
  //.txt or .dat file
  Serial.println("File Name: " + String(num));
  fileNm = String(num) + /*".dat"*/".txt";
  num++;

  //WRITING TO FILE
  myFile = SD.open(FILE_NAME, FILE_WRITE);
  if(myFile) {
    myFile.println();
    myFile.print(int(num));
    myFile.close();
  }else{
    Serial.println("error opening");
  }

  //INITIALIZE NEW FILE
  myFile = SD.open(String(fileNm), FILE_WRITE);
  myFile.println("Time\tPitot\tThrust\tAX\tAY\tAZ");
  myFile.close();

  start = millis();
  delay(1000);
}

void loop(){
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr,14,true);
  AcX=Wire.read()<<8|Wire.read();
  AcY=Wire.read()<<8|Wire.read();
  AcZ=Wire.read()<<8|Wire.read();

  AcX /= constant;
  AcY /= constant;
  AcZ /= constant;
  
  pitot = analogRead(A0); 
  thrust = analogRead(A2);

  if((millis()- start) % INTERVAL == 0){
    Serial.print(String(millis()) + "\t");
    
    Serial.print("Pitot Value: " + String(pitot));   
    Serial.print("  Thrust Value: " + String(thrust));

    Serial.print("  AcX = " + String(AcX) + "m/s^2");
    Serial.print("  AcY = " + String(AcY) + "m/s^2");
    Serial.println("  AcZ = " + String(AcZ) + "m/s^2");

    
    myFile = SD.open(fileNm, FILE_WRITE);
    if(myFile) {
      myFile.println(String(millis()) + "\t" + String(pitot) + "\t" + String(thrust) + "\t" + String(AcX) + "\t" + String(AcY) + "\t" + String(AcZ));
      myFile.close();
    }else{
      Serial.println("error opening " + String(fileNm));
    }
    
  }

}
