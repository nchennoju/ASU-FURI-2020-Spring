# ASU-FURI-2020-Spring
Propulsion Data Acquisition          ASU FURI project. Second Semester Freshman.

Objective:

The objective of this FURI research project is to examine the thrust potential of an aircraft at different airspeeds. This project will be expanding on another student’s previous research project, but will incorporate more accurate data filtration and collection methods with multiple sensors on board to verify results. The main objective is to collect data relevant to aircraft performance. With the data collected, aircrafts can be efficiently tuned in throttling power which could be used to maximize an aircraft’s range.

Background:

The sensors that will be used in this project are: GPS, accelerometer/gyroscope, pitot tube, and force sensitive resistor (FSR). 
Data from the Pitot tube will be used to determine the airspeed of the aircraft in the air. This data can then be differentiated to obtain acceleration data (assuming the data is filtered).
Data from the accelerometer will be used to verify thrust data. The 3 axis accelerometer can be configured to eliminate the acceleration component of gravity from the results. With this, the accelerometer data will present the net acceleration of the aircraft as given by the equations:
Fnet = FT + FD
manet= FT + FD, with FD= 12CAv2anet= 1m( FT +  12CDAv2): m(mass), CD(Drag Coefficient), (fluid density), A(area), v(airspeed) 

If this equation is solved for FT, the accelerometer data provides a verification to the Thrust data provided by the FSR sensor assuming drag force is calculated onboard and subtracted from the result. The drag force component of the equation can be calculated by incorporating the data from the pitot tube. Considering that the drag coefficient cannot be numerically computed, an experiment will need to be devised in order to experimentally determine the value of Cd. Since accelerometer data will only be used to verify data, this experiment may not be needed. The accelerometer data will enable the digital filter onboard the arduino to set thresholds for any input data from the sensors being verified. With this plan for data verification, not much precision will be needed to determine threshold values.

The acceleration data will also be used to verify the velocity data. Considering that the change in pitot velocity data represents the acceleration, a new threshold can be determined based on acceleration data.

All sensors used with the arduino have a noise component with their sensor output. Considering this, it is necessary to use both an analog and digital filter before analyzing data. The analog filter will be based on a RC circuit. The general circuit diagram for an analog filter is as follows:

The values of the capacitor of the resistor and capacitor can be varied to provide different levels of filtration. These values should be based on the frequency at which data is read. The formula used to determine the values of the resistor and capacitor is as follows: The only problem with the following filter is that it can only be used for the pitot and thrust sensor. Considering that the GPS and accelerometer/gyro consists of signals that are interpreted differently by a separate on board library, a form a digital filtration will be have to be used in addition to the analog filter. This digital filter will be implemented via a separate program that is run postflight. Some characteristics of this digital filter is to incorporate different levels of filters that eliminate unwanted oscillations. Some example filters are:
Moving Average: value = average of last n values
Derivative Threshold: values that are only within a certain threshold will be added to the .txt file
Difference Threshold: values are added only if difference between current # and previous # is within threshold
Divide Integer Conversion: # is divided by n and converted to an integer

This project will use a combination of an arduino, an analog filter (RC circuit), and a digital filter (Java program) in order to output close to accurate sensor data. Since all components of the data collection process operate on different platforms, part of the project will be to interface these components in order to improve the usability of the data collected.

Since this research project will be using the propellor as the method of propulsion, an understanding of how a propeller is designed/optimized is essential to understand. 

Problem Statement:

Considering that I am expanding on Daniel’s research project to obtain data regarding the thrust produced at different air speeds, I will be looking at different methods to make the data obtained more reliable. Previously, air devils made their first attempt at determining the relationship between airspeed and potential thrust using a scaled wind tunnel. Since the diameter of the tunnel limited the props that could be tested, a new method to test the efficiency of propellers was needed. This was when Daniel Kosednar decided to move all equipment onto a trainer aircraft. With this he was able to obtain noisy data that gave a general idea of the thrust vs. airspeed curve. This project aims to increase the accuracy of the data collected while also experimenting different methods of sensor filtration methods.
Plan of Action:

With the combination of the Arduino, an analog filter, a digital filter, and a data presentation program, I will be able to convert flight data into usable information which helps determine the optimal propulsion power level in order to maximize range. With the help of the two levels of filtration, I am hoping to obtain results which consist of minimal noise which in turn can be used to make more detailed calculations on the data.

Expected Outcomes:

At the end of this project, I am expecting to have developed a tool that can be used to optimize and aircraft's throttling of power in order to increase range. By the time all the data is collected, the tools used to collect data will be developed enough to provide accurate sensor data while being connected through an interface that will display data in a clean and organized fashion. Based on my hypothesis, I am looking forward to seeing the thrust potential of the propulsion device of a propellor decrease as airspeed increases.

Bibliography:

https://furi.engineering.asu.edu/furiproject/data-enabled-dynamic-thrust-measurement/

ISBN: 978-013-283288-5 (pg. 354)

Boylestad, Electronic Devices and Circuit Theory

Halliday + Resnick, Fundamentals of Physics 7th Edition

H>C> “Skip” Smith, The illustrated Guide to Aerodynamics

Timeline:

Time
Description
Nov. 1, 2019 - Dec. 1, 2019
Research implementing different methods of filtration (digital + analog)
Dec. 1, 2019 - Jan. 31, 2020
Complete PCB consisting of arduino + onboard sensors
Jan. 15, 2020 - Feb. 15, 2020
Design + Print PCB/Sensor Mounts
Feb 15. 2020 - March 1. 2020
Finalize Digital + Analog FIlter
Feb 1, 2020 - Feb 15, 2020
Calibrate Sensors (conversion of sensor signal to SI units)
Feb 15. 2020, March 15. 2020
Finalize Arduino Program to use sensor data to cross-reference data from sensors
March 1, 2020 - March 15, 2020
Identification of Thrust vs. Airspeed Graph
March 15, April 15, 2020
Test Flights (2-3)
April 1, 2020 - April 15, 2020
Interface components of research
April 1, 2020 - Project Due Date
Finalize Report + Deliver FURI Presentation


Personal Statement:

My name is Nitish and I have always had a passion for aerospace, electrical, and computer engineering. My passions began when I was in 3rd grade when I had taken on the task to present weekly to my class on a science topic of my interest. I presented on topics ranging from erosion to the physics of pulleys. This was the spark that fueled my interest in science. As I progressed, I began to help people more. I would help my teachers in very simple wiring of the projector and doc camera. This was the beginning to my interest in technology. I began experimenting with basic electronics and participated in my first robotics competition: First Lego League, a lego robotics competitions for kids. I had participated in this competition for 3 years before finally moving onto the next levels, First Tech Challenge and First Robotics Competition in high school. More recently, I began having a passion for physics after spending 2 years studying the basics of the subject: Physics H. + AP Physics C: Mech + Elec/Mag. To give myself an opportunity to apply my physics knowledge to another passion, I decided to start a club on something I have looked to participate in since I was a child: model rocketry. After teaching myself the fundamentals of model rocketry and aerodynamics, I decided that it was time to start a club in High School which I decided to call the Aviation and Rocketry club. Consequently, with this knowledge, I was able to lead my boy scout troop in a troop wide merit badge of Space Exploration during my troop’s annual summer camp, begin a personal research project (Arduino UAV) which I will continue working on during my free time in college, and work on model rocketry stabilization methods. All of my passions make me a great candidate to complete my proposed research project.
I’m interested in participating in FURI research in order to give me a challenge in college that will make use of all the things I have learnt before. FURI also gives me the opportunity to expand my own knowledge in different aspects of aerospace, electrical and computer engineering that I have not studied. Considering that I am interested in aerospace, electrical, and computer engineering, this FURI project will give me an opportunity to work on a project that involves all 3 of my passions.

Resume Link: https://tinyurl.com/ResumeNitish
