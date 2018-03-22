# FRC-2018
This is the robot code for the 2018 FRC game. All of the code is written in Java except for the vision code in Python.

## Libraries we Use

1. navx_frc*
2. WPILib*
3. cscore
4. networktables
5. opencv
6. CTRE_Phoenix
7. CTRE_Phoenix-Sources
8. niVisionWPI
9. wpiUtil

*These libraries will require you to set their path every time you import the project, the rest of them set themselves automatically

## Steps to Deploy Code

1. Connect to the radio through Ethernet, Configure the Radio to have the most recent firmware
2. Deploy the 2018 firmware to the roborio
3. Connect to the Robo Rio through USB
4. Run the Phoenix Lifeboat program on your computer to download the CTRE libraries onto the Rio
5. Use 172.22.11.2 in Internet Explorer with Microsoft Silverlight to see the roborio interface
6. Update the firmware for the PDP and PCM, make sure the ports are set correctly
7. Update the firmware for the Talons, make sure the ports are set correctly on them
8. Deploy the code onto the rio

## Other Information

The code is set up to use SmartDashBoard for competition, though Shuffleboard works too

Our current Main branch is Auto-Logic-Testing
