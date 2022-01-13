% Darrick Ross
% REDACTED
% Final Project - Matlab
%
% Program 2 Travel Time In this problem, you must create a program that
% prompts the user for a single choice, between a bicycle, car, or jet
% plane. Once the choice is made, you should pick a random speed for the
% vehicle, defined by the ranges below, and travel for "one hour" at that
% speed. You should continue iterating, pick a new speed each hour, until
% your vehicle travels 1000 miles. You should then print how many hours it
% took, and the maximum speed reached.

% The speed ranges for the three vehicles are as follows:
%
% Bicycle: 5-15 miles per hour
% Car: 20-70 miles per hour
% Jet Plane: 400-600 miles per hour

%==========================================================================
% How to Use:
%   Everything is built into the program. All you need to do is run the
%   program, and input either 1, 2, or 3 corresponding to the different
%   vehicles. It will then print the information about how long it took to
%   travel 1000 miles with that vehicle. If you would like to change how
%   far the program uses please change the variable on line 35.
%==========================================================================

% Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

format long; %change the output format to

milesTraveled = 0;
milesToTravel = 1000;
hoursSpent = 0;
currentSpeed = 0;
maxSpeedBicycle = 15;
minSpeedBicycle = 5;
maxSpeedCar = 70;
minSpeedCar = 20;
maxSpeedJet = 600;
minSpeedJet = 400;
highestSpeed = 0;
differenceInSpeed = 0;
minSpeed = 0;

disp("Pick a vehicle:");
disp("1.  Bicycle - Speed(" + minSpeedBicycle + " to " + maxSpeedBicycle + ")");
disp("2.  Car - Speed(" + minSpeedCar + " to " + maxSpeedCar + ")");
disp("3.  Jet Plane - Speed(" + minSpeedJet + " to " + maxSpeedJet + ")");

choice = input('Choose 1-3: ');

switch choice
    case 1
        differenceInSpeed = maxSpeedBicycle - minSpeedBicycle;
        minSpeed = minSpeedBicycle;
    case 2
        differenceInSpeed = maxSpeedCar - minSpeedCar;
        minSpeed = minSpeedCar;
    case 3
        differenceInSpeed = maxSpeedJet - minSpeedJet;
        minSpeed = minSpeedJet;
end

while (milesTraveled < milesToTravel)
    hoursSpent = hoursSpent + 1;

    %Obtain a random number weighted to the max speed divided by
    currentSpeed = differenceInSpeed*rand + minSpeed;
    currentSpeed = round(currentSpeed);
    milesTraveled = milesTraveled + currentSpeed;

    if (currentSpeed > highestSpeed)
        highestSpeed = currentSpeed;
    end
end



fprintf("The number of hours it took to travel %d miles was %d hours.\n", milesToTravel, hoursSpent);
fprintf("The maximum speed was %d mph.\n", highestSpeed);
