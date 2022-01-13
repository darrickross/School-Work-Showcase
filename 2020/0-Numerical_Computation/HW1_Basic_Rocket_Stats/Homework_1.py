# Darrick Ross
# Homework 1
# September 22 2020

_80_Equals = "================================================================================"

#Constants
__rho = 1.293               # kg / m^3
__EGravCon = 9.80665        # m / s^2
__EngRedCon = -0.0001644    # ??

#meters
length = 0.311
diameter = 0.0241
distance = 0

#meters squared
area_body = 0.506E-3
area_fins = 0.00496

#no units
Cdrag_body = 0.45
Cdrag_fins = 0.01

#kg
mass_body = 0.0340
mass_engine_init = 0.0242
mass_engine_curr = mass_engine_init
mass_engine_final = 0.0094
mass_total = mass_body + mass_engine_init

#seconds
time = 0
dt = 0.1

#meters per second
velocity = 0

# meters per second ^ 2
acceleration = 0

#Thrust
thrust_curve = [0, 6, 7, 9, 10, 10, 8, 7, 6, 5, 5, 4, 3, 3, 2, 2, 1, 1 ,1, 0, 0]
thrust_index = 1

def getEngineThrust():
    try:
        return thrust_curve[thrust_index]
    except:
        return 0

# ==============================================================================
# MAIN
# ==============================================================================

def main():
    #pull in these variables so they write to the global variables not local
    global distance, time, velocity, acceleration, mass_total, thrust_index, mass_engine_curr

    continue_loop = True

    print("Starting\n\n")

    while ( continue_loop == True):
        #Update stats for each dt

        #Force drag using last velocity
        Fd_body = Cdrag_body * __rho * area_body * velocity * velocity / 2
        Fd_fins = Cdrag_fins * __rho * area_fins * velocity * velocity / 2

        #Force Grav
        Fg = mass_total * __EGravCon

        #Net Force
        Force_Net = getEngineThrust() - (Fd_body + Fd_fins + Fg)

        #Acceleration
        acceleration = Force_Net / mass_total

        #DV
        dv = acceleration * dt

        #Velocity
        velocity = velocity + dv

        #ds
        ds = velocity * dt

        #distance
        distance = distance + ds

        #update engine
        mass_engine_curr = mass_engine_curr + (getEngineThrust() * __EngRedCon)   #__EngRedCon is negative
        if (mass_engine_curr < mass_engine_final):
            mass_engine_curr = mass_engine_final

        #update the total mass
        mass_total = mass_body + mass_engine_curr

        #update time
        time = time + dt

        #update thrust curve index
        thrust_index = thrust_index + 1

        #print
        print("T: {:.1f} | S: {:09.4f} | V: {:08.4f} | A: {:08.4f} | F: {:07.4f} | M {:.5f}".format(time, distance, velocity, acceleration, Force_Net, mass_total))

        if (velocity < 0):
            continue_loop = False

    print(_80_Equals)
    print("Velocity went negative")



if __name__ == "__main__":
    main()
