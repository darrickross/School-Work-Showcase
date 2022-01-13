; File: atbash.asm
;
; Encrypt the data using atbash cipher
;
; Assemble using NASM:  nasm -f elf64 atbash.asm
; Link with ld:  ld atbash.o -o atbash
;

%define STDIN         0
%define STDOUT        1
%define SYSCALL_EXIT  60
%define SYSCALL_READ  0
%define SYSCALL_WRITE 1
%define BUFLEN        256


        SECTION .data                ; initialized data section

msg1:   db "Enter string: "          ; user prompt
len1:   equ $-msg1                   ; length of first message

msg2:   db "Original:  "             ; original string label
len2:   equ $-msg2                   ; length of second message

msg3:   db "Converted: "             ; converted string label
len3:   equ $-msg3

msg4:   db 10, "Read error", 10      ; error message
len4:   equ $-msg4


        SECTION .bss                 ; uninitialized data section
buf:    resb BUFLEN                  ; buffer for read
newstr: resb BUFLEN                  ; converted string
count:  resb 4                       ; reserve storage for user input bytes


        SECTION .text                ; Code section.
        global  _start               ; let loader see entry point

_start: nop                          ; Entry point.
start:                               ; address for gdb

        ; prompt user for input
        ;
        mov rax, SYSCALL_WRITE       ; write function
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg1                ; Arg2: addr of message
        mov rdx, len1                ; Arg3: length of message
        syscall                      ; 64-bit system call

        ; read user input
        ;
        mov rax, SYSCALL_READ        ; read function
        mov rdi, STDIN               ; Arg1: file descriptor
        mov rsi, buf                 ; Arg2: addr of message
        mov rdx, BUFLEN              ; Arg3: length of message
        syscall                      ; 64-bit system call

        ; error check
        ;
        mov [count], rax             ; save length of string read
        cmp rax, 0                   ; check if any chars read
        jg  read_OK                  ; >0 chars read = OK
        mov rax, SYSCALL_WRITE       ; Or Print Error Message
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg4                ; Arg2: addr of message
        mov rdx, len4                ; Arg3: length of message
        syscall                      ; 64-bit system call
        jmp     exit                 ; skip over rest
read_OK:


        ; Loop for upper case conversion
        ; assuming count > 0
        ;
L1_init:
        mov     rcx, [count]         ; initialize count
        mov     rsi, buf             ; point to start of buffer
        mov     rdi, newstr          ; point to start of new string

L1_top:
        mov     al, [rsi]            ; get a character
        inc     rsi                  ; update source pointer
		
        cmp     al, 'A'              ; if al has a char below
        jb      L1_cont				 ; 'A' go to L1_cont
        
		cmp     al, 'Z'              ; if al has a char below
        jb      cipher_upper		 ; 'Z' go to cipher_upper
		
		cmp     al, 'a'              ; if al has a char below
        jb      L1_cont				 ; 'a' go to L1_cont
		
        cmp     al, 'z'              ; if al has a char below
        jb      cipher_lower		 ; 'z' go to cipher_lower
		
        jmp		L1_cont		         ; skip this char
		
cipher_upper:
		cmp		al, 'N'				 ; check if above 'N'
		ja		upper_first_half	 ; if so go to upper_first_half
		
		; otherwise we deal with 2nd half here
		mov ebx, eax				 ; mov a copy of the letter to ebx
		sub ebx, 'N'				 ; get difference from 'N' (ebx has diff)	
		mov eax, 'M'				 ; Place 'M' in eax
		sub eax, ebx				 ; subtract difference from 'M'
		
		jmp		L1_cont				 ; go to L1_cont with al containing new al
		
upper_first_half:
		mov ebx, eax				 ; mov a copy of the letter to ebx
		sub ebx, 'M'				 ; get diff from 'M' (ebx has diff, this will be negative)	
		neg ebx						 ; get the positive diff
		mov eax, 'N'				 ; Place 'N' in eax
		add eax, ebx				 ; add difference from 'M'
		
		jmp		L1_cont				 ; go to L1_cont with al containing new al
		
cipher_lower:
		cmp		al, 'n'				 ; check if above 'n'
		ja		lower_first_half	 ; if so go to upper_first_half
		
		; otherwise we deal with 2nd half here
		mov ebx, eax				 ; mov a copy of the letter to ebx
		sub ebx, 'n'				 ; get difference from 'n' (ebx has diff)	
		mov eax, 'm'				 ; Place 'm' in eax
		sub eax, ebx				 ; subtract difference from 'm'
		
		jmp		L1_cont				 ; go to L1_cont with al containing new al
		
lower_first_half:
		mov ebx, eax				 ; mov a copy of the letter to ebx
		sub ebx, 'm'				 ; get diff from 'm' (ebx has diff, this will be negative)	
		neg ebx						 ; get the positive diff
		mov eax, 'n'				 ; Place 'n' in eax
		add eax, ebx				 ; add difference from 'm'
		
		jmp		L1_cont				 ; go to L1_cont with al containing new al

		

L1_cont:
        mov     [rdi], al            ; store char in new string
        inc     rdi                  ; update dest pointer
        dec     rcx                  ; update char count
        jnz     L1_top               ; loop to top if more chars
L1_end:


        ; print out user input for feedback
        ;
        mov rax, SYSCALL_WRITE       ; Print Message
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg2                ; Arg2: addr of message
        mov rdx, len2                ; Arg3: length of message
        syscall                      ; 64-bit system call

        mov rax, SYSCALL_WRITE       ; Write user input
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, buf                 ; Arg2: addr of message
        mov rdx, [count]             ; Arg3: length of message
        syscall                      ; 64-bit system call

        ; print out converted string
        ;
        mov rax, SYSCALL_WRITE       ; Print Message
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg3                ; Arg2: addr of message
        mov rdx, len3                ; Arg3: length of message
        syscall                      ; 64-bit system call


        mov rax, SYSCALL_WRITE       ; Write out string
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, newstr              ; Arg2: addr of message
        mov rdx, [count]             ; Arg3: length of message
        syscall                      ; 64-bit system call


        ; final exit
        ;
exit:   mov rax, SYSCALL_EXIT
        mov rdi, 0                   ; exit to shell
        syscall
		