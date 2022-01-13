;Darrick Ross
;4/23/18
;CMSC331 section 01

;3
(define cone-facts
	(lambda (radius height)
		(list 
			(* (/ 1 3) 3.14 radius radius height) 
			(+ 
				(* 3.14 radius radius)
				(* 3.14 radius 
					(sqrt (+ 
						(* radius radius) 
						(* height height)
						)
					)
				)
			)
		)
	)
)


;4
(define double-rotate
	(lambda x
		(append 
			(cdr (cdr (car x))) 
			(list (car (cdr (car x))))
			(list (car (car x)))
		)
	)
)



;5
(define slice
	(lambda (theList startPoint endPoint)
		;If start point is not 0, recurs with this list minus the first element, and -1 from start and end points
		(if (= startPoint 0)
			;if the end point is 0 then return empty list
			;if its greater than zero append the first element with a recurse call off this fucntion with
			;the list minus the first element, the same startPoint (its zero at this time), and endpoint - 1
			
			(if (> endPoint 0)	
				(append
					(cons (car theList) null)
					(slice (cdr theList) startPoint (- endPoint 1))
				)
				(list)
			)
			
			(slice (cdr theList) (- startPoint 1) (- endPoint 1))
		)
	)
)





;6
(define remove-first
	(lambda (theList search)
		(if (null? theList)
			null
			(if (equal? (car theList) search)
				(cdr theList)
				(append 
					(cons (car theList) null)
					(remove-first (cdr theList) search)
				)
			)
		)
	)
)







;7
(define perfect-squares
	(lambda (numbers)
		(filter 
			(lambda (x)
				(integer? (sqrt x))
			)
			numbers
		)
	)
)










;8
(define sd
	(lambda (numbers)
		(let
			((avg (average numbers)))
			
			(sqrt 
				(/ 
					(apply +
						(map 
							(lambda (x)
								(* x x)
							)
							
							(map 
								(lambda (y)
									(- y avg)
								)
								numbers
							)
						)
					)
					
					(- (length numbers) 1)
				)		
			)
		)
	)
)


(define average
	(lambda (li)
		(/ (apply + li)
			(length li)
		)
	)
)



