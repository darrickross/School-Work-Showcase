# Overview

- `MultiPrecision_Output.txt` : The example output
- `MultiPrecision.py` : The main Driver
- `README.md` : This file.

This assignment was to calculate the exact global maximum of a function.  
```
z = exp(sin(50.0*x)) + sin(60.0*exp(y)) + sin(80.0*sin(x)) + sin(sin(70.0*y))
    - sin(10.0*(x+y)) + (x*x+y*y)/4.0
```

## Process

*Note `dx` and `dy` are the same variable in this.*

1. Starting from a specific `(x,y)`, `dx`, and `dy`.
  1. `x=0.469`
  2. `y=-0.923`
  3. `dx=dy=0.001`
2. Calculate the 8 offsets of `(x,y)` using `dx` and `dy` as offsets.
  1. f(`x-dx` , `y+dy`)
  2. f(`x` , `y+dy`)
  3. f(`x+dx` , `y+dy`)
  4. f(`x+dx` , `y`)
  5. f(`x+dx` , `y-dy`)
  6. f(`x` , `y-dy`)
  7. f(`x-dx` , `y-dy`)
  8. f(`x-dx` , `y`)
3. **If** none of the 8 options is larger than `f(x,y)`
  1. Halve `dx`.
    - *`dx=dy` at all times*
4. **Else If** one of the 8 are larger, choose the largest,
  1. Update the `x`, `y` values with the chosen coordinate that results in the largest `f(x,y)`.
5. **Finally** repeat until the precision at the specified number of digits (`100`) no longer changes.

# Output
```
Starting Project
starting x=  0.469
starting y=  -0.923
Global Minimum Calculated as:
x=   0.468653872305889018244712196399122591252642960891934729345726971892241295556361133866780567994958347261162919488265127818342400710674997540624337528855
y=  -0.922916516132714645371768517867109177468961343782094530752146965843095820174429065751597194030977353605835856019219880238174553246150355319224216826142
z=  -3.13833328825201157264583023768075830013907533279142278217980617433295817358479867077164328528634411800654647943189033293418587233538830250115858009839
dx =  6.1098727269992093641039587864272353995788054811446533277872278185625832530494409154037144702160304565137503053871809343106357068556085679394702662284e-151
```
