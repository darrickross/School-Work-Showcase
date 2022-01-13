
--[[
Darrick Ross
CMSC 331
Homework 4
]]

--[[Average array
Write a function avg(array) that takes in one argument, an array of numbers, 
and returns the average of them. You may not use any library functions to complete this problem.
You may assume that the array always holds numbers, but it could be an empty array as well.
]]


function avg(array)
	index = 1
	total = 0
	while array[index] ~= nil do
		total = total + array[index]
		index = index + 1
	end
	index = index - 1       --index now represents the size of the array
	if total > 0 then
		return total/index
	end
	return 0
end

--[[Integer Division
Because Lua only has one data type for numbers, it does not provide any ability to do 
integer division. For this problem, write a function, int_divide(a,b), that takes in 
two numbers, and returns the result of dividing a by b. You should use some type of 
loop to achieve this, repeatedly subtracting b from a until you cannot do so any more. 
You may assume both the numerator and denomenator will always be positive. You may not 
use any library functions to complete this problem.
]]

function int_divide(a, b)
	quotient = 0
	dividend = b
	while dividend > a	do
		dividend = dividend - a
		quotient = quotient + 1
	end
	return quotient
end

--Advanced Functions
--[[Volume and Surface Area of a Cone
For this problem, you need to write a function called cone_facts(r,h) that takes in 
the radius of the base and the height of the cone, and returns two values, the volume 
of the cone, and the surface area of the cone. They must be returned in that order

V = (1/3) pi r^2 h

SA = pi r^2 + pi r sqrt(r^2 + h^2)
]]

function cone_facts(r,h)
	volume = 0
	surfaceArea = 0
	
	volume = (1/3)*(math.pi)*(math.pow(r,2))*h
	surfaceArea = (math.pi * math.pow(r,2)) + (math.pi * r * math.sqrt(math.pow(r,2) + math.pow(h,2)))
	
	return volume, surfaceArea
end

--[[String Join
Write a function join(a,b,c,d) that takes up to 4 strings, and joins them together 
with a comma. Your function should still work when given less than 4 strings, 
including printing out just one string with no commas attached when given only one 
string as an argument.
]]

--[[Lazy method
function join(a,b,c,d)
	currentString = ''
	if a == nil then
		return currentString
	else
		currentString = a
	end
	if b == nil then
		return currentString
	else
		currentString = currentString .. ', ' .. b
	end
	if c == nil then
		return currentString
	else
		currentString = currentString .. ', ' .. c
	end

	if d == nil then
		return currentString
	else
		currentString = currentString .. ', ' .. d
	end
	return currentString
end
]]


function join(a,b,c,d)
	strings = {a, b, c, d}
	currentString = ''
	index = 1
	if strings[index] ~= nil then
		currentString = a
		index = 2
	end
	while strings[index] ~= nil do
		currentString = currentString .. ', ' .. strings[index]
		index = index + 1
	end
	return currentString
end	

--[[Read in from a file
Write a function read_elements(f_name) that reads in the file located at the 
location specified by f_name. This file consists of lines with two space-separated 
columns, the first column being the name of a chemical element, and the second 
column being the year it was isolated. Your function should return a table of 
tables, represeting this information from the file.
]]

function read_elements(f_name)
	SEPARATOR = '  '                --What separates each column
	file = io.open(f_name, r)       -- open file of given name
	tableOfElements = {}            --Holds all sub tables of data

	--for each line in the file, find the location of the double space.
	--Put the part before the double space in index 1 of a subTable, content after
	--is then put the subTable index 2. Then puts the row into tableOfElements.
	--then returns tableOfElements
	size = 1
	for lineInFile in file:lines() do
		subTable = {}
		findPos = string.find(lineInFile, SEPARATOR)
		if findPos ~= nil then
			subTable[1] = string.sub(lineInFile, 1, (findPos - 1) )
			subTable[2] = string.sub(lineInFile, findPos + 2)
			tableOfElements[size] = subTable
		end
		size = size + 1
        end

	file:close()
	return tableOfElements
end
	

	
--[[Sorting the Table By Year
Take a table of the format generated in the previous example, and write a 
function sort_by_year(table_name) that sorts the elements by the year they 
were discovered, and prints the information to the screen. An example run 
is shown below.
]]
function specialSortFunction(a,b)
	return a[2] < b[2]
end


function sort_by_year(table_name)
	--sort table_name
	table.sort(table_name, specialSortFunction)

	--print
	for index,subElementTable in pairs(table_name) do
		print (subElementTable[1] .. '\t' .. subElementTable[2])
	end
end

--Lexical Scanner

--[[Number Format Scanner
Write a function scan(num_string) that reads in a single character at a time, 
and when it reaches end of the string, reports whether it has read in a decimal, 
octal, or hexadecimal number, and what that number is (as a string), or "NONSENSE" 
if it isn't a well-formed number.

For the purposes of this assignment, use the following definitions of decimal, 
octal, and hexadecimal numbers

decimal - A single zero, or any continous sequence of decimal digits that starts 
with a non-zero decimal digit and includes 0, 1, 2, 3, 4, 5, 6, 7, 8, 9

octal - An octal number always starts with a 0, and is followed by a single 0 
or a non-empty sequence of octal digits that does not start with 0. The octal 
digits are 0, 1, 2, 3, 4, 5, 6, and 7.

hexadecimal - A hexadecimal number always starts with a 0 followed by a lowercase 
'x' and either a single 0 or a sequence of hexadecimal digits. Hexadecimal digts 
include 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e, f, A, B, C, D, E, and F.

All forms can have an optional positive or negative sign directly infront of the 
number, with no space between the symbol and the number.
]]

function scan(num_string)
	isDec = true
	isOct = true
	isHex = true

	--obtain size of string
	size = 0
	while string.sub(num_string, size+1, size+1) ~= '' do
		size = size + 1
	end

	--negative number
	startOfNumber = 1
	if (string.sub(num_string, 1, 1) == '-') then
		startOfNumber = 2
	end

	--test decimal
	if (string.sub(num_string, startOfNumber, startOfNumber) == '0') and (size > startOfNumber) then
		isDec = false
	else
		for index = startOfNumber+1, size do
			if (string.sub(num_string, index, index) < '0') or (string.sub(num_string, index, index) > '9') then
				isDec = false
				break
			end
		end
	end

	--test octal
	if (string.sub(num_string, startOfNumber, startOfNumber) == '0') then
	--check if its just 00
		if (string.sub(num_string, startOfNumber+1, startOfNumber+1) == '0') and (size > startOfNumber+1) then
			isOct = false
		elseif string.sub(num_string, startOfNumber) == '0' then
			isOct = false
		else
			for index = startOfNumber+1, size do
				if (string.sub(num_string, index, index) < '0') or (string.sub(num_string, index, index) > '7') then
					isOct = false
					break
				end
			end
		end
	else
		isOct = false
	end

	--test Hex
	if string.sub(num_string, startOfNumber, startOfNumber+1) == '0x' then
		for index = startOfNumber +2, size do
			if (string.sub(num_string, index, index) < '0') or (string.sub(num_string, index, index) > '9') then
				if (string.sub(num_string, index, index) < 'A') or (string.sub(num_string, index, index) > 'F') then
					if (string.sub(num_string, index, index) < 'a') or (string.sub(num_string, index, index) > 'f') then
						isHex = false
					end
				end
			end
		end
	else
		isHex = false
	end

	--print result
	if isDec or isOct or isHex then
		if isDec then
			print('This number is a valid Decimal Number')
		end

		if isOct then
			print('This number is a valid Octal Number')
		end

		if isHex then
			print('This number is a valid Hexadecimal Number')
		end
	else
		print('This number is NONSENSE')
	end

	print('The number is ' .. num_string)
end


--[[Bank Account
Write a function make_account(currency_string) that takes in one argument, that is a string representing a currency, 
and returns three functions. The first function takes one argument and allows the user to deposit money into their 
bank account. The second function also takes one argument, and allows users to withdraw money from their bank 
account. The third function takes no arguments, and allows users to see their balance, displayed according to the 
currect currency. You must use a closure so the balance is remembered between transactions.

The bank account should start out with 0 dollars, and is allowed to go into the negative.
]]

function make_account(currency_string)
local balance = 0
local currency_type = currency_string

return 
	function(deposit_amount)
		balance = balance + deposit_amount
	end,
	
	function(withdraw_amount)
		balance = balance - withdraw_amount
	end,
	
	function()
		print('You have ' .. balance .. ' ' .. currency_type .. ' in your account')
	end
end

