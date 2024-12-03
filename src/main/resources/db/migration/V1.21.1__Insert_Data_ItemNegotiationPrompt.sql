INSERT INTO ItemNegotiationPrompt (ItemNegotiationPromptId, ScriptLineId, PersonalityId, CanBeInitialOffer, CanBeRevisedOffer, CanBeRepeatOffer, CanBeRejection, CanBeAcceptance, 0, 0, 0)
VALUES
      -- Creeper
      (0, 400, 1, 1, 0, 0, 0, 0) -- "I just ssso happen to have thisss %ITEM% ssspare."
     ,(1, 401, 2, 1, 0, 0, 0, 0) -- "Isss thisss %ITEM% enough?"
     ,(2, 402, 3, 1, 0, 0, 0, 0) -- "Take thisss %ITEM% and go would you?"
     ,(3, 403, 4, 1, 0, 0, 0, 0) -- "Thisss %ITEM% is probably the best thing I have..."

     ,(4, 404, 1, 0, 1, 0, 0, 0) -- "Alright, let'sss upgrade that to thisss %ITEM%."
     ,(5, 405, 2, 0, 1, 0, 0, 0) -- "What!? How about thisss %ITEM%?"
     ,(6, 406, 3, 0, 1, 0, 0, 0) -- "Don't be ssso cocky. Take this %ITEM%."
     ,(7, 407, 4, 0, 1, 0, 0, 0) -- "Oh... what about this %ITEM%?"

     ,(8, 408, 1, 0, 1, 0, 0, 0) -- "More? Here'sss a ssspecial just for you then, thisss %ITEM%."
     ,(9, 409, 2, 0, 1, 0, 0, 0) -- "I'm not sssure how much more I have, isss this %ITEM% enough?"
     ,(10, 410, 3, 0, 1, 0, 0, 0) -- "Persssissstent, aren't we? %ITEM%?"
     ,(11, 411, 4, 0, 1, 0, 0, 0) -- "Sssucks to be me... isss this %ITEM% enough?"

      -- Creeper / Accept
     ,(12, 412, 1, 0, 0, 0, 0, 1) -- Sssweet. Have fun with it!
     ,(13, 413, 2, 0, 0, 0, 0, 1) -- Here it isss...
     ,(14, 414, 3, 0, 0, 0, 0, 1) -- Expensssive tastes... Fine.
     ,(15, 415, 4, 0, 0, 0, 0, 1) -- Enjoy. It''sss worth more than me anyway...

      -- Creeper / Reject
     ,(16, 416, 1, 0, 0, 0, 1, 0) -- Inssstead, let''sss party.
     ,(17, 417, 2, 0, 0, 0, 1, 0) -- Sssorry, I can''t offer any more.
     ,(18, 418, 3, 0, 0, 0, 1, 0) -- Enough. Let''sss gamble life inssstead.
     ,(19, 419, 4, 0, 0, 0, 1, 0) -- My life''sss not worth any more. Take it inssstead.


      -- Skeleton
     ,(20, 420, 1, 1, 0, 0, 0, 0) -- "I gOt thIs %ITEM%, jUst doN't aSk whEre I've bEen keePin' it."
     ,(21, 421, 2, 1, 0, 0, 0, 0) -- "MigHt haVe thiS %ITEM%?"
     ,(22, 422, 3, 1, 0, 0, 0, 0) -- "TaKe thiS %ITEM% aNd jOg oN!"
     ,(23, 423, 4, 1, 0, 0, 0, 0) -- "I gOt noThiN' buT thIs %ITEM%."

     ,(24, 424, 1, 0, 1, 0, 0, 0) -- "I aIn't gOt aNy poCkeTs y'kNow. I cAn dIg oUt thiS %ITEM%?"
     ,(25, 425, 2, 0, 1, 0, 0, 0) -- "AnYthIn' foR sOme eXtrA liFe, tAke tHis %ITEM%!"
     ,(26, 426, 3, 0, 1, 0, 0, 0) -- "TyPicaL flEshbeAreR, lOoKin' fOr eVen moRe. TaKe thIs %ITEM%."
     ,(27, 427, 4, 0, 1, 0, 0, 0) -- "Be taKin' thIs %ITEM%."

     ,(28, 428, 1, 0, 1, 0, 0, 0) -- "I'd SaY yoU'rE flEeciN' mE, bUt i'D neEd sKin fOr thAt. HeRe's thIs %ITEM%."
     ,(29, 429, 2, 0, 1, 0, 0, 0) -- "%ITEM% fOr yoU..."
     ,(30, 430, 3, 0, 1, 0, 0, 0) -- "%ITEM%. TaKe it Or lEavE iT."
     ,(31, 431, 4, 0, 1, 0, 0, 0) -- "%ITEM%...?"

     -- Skeleton / Accept
     ,(32, 432, 1, 0, 0, 0, 0, 1) -- EnJoy. Be sEeyiN'' yA''.
     ,(33, 433, 2, 0, 0, 0, 0, 1) -- ThAnkS.
     ,(34, 434, 3, 0, 0, 0, 0, 1) -- EnJoY, flEshSacK.
     ,(35, 435, 4, 0, 0, 0, 0, 1) -- ...K.

     -- Skeleton / Reject
     ,(36, 436, 1, 0, 0, 0, 1, 0) -- LOoKs lIke wE goT a boNe to piCk.
     ,(37, 437, 2, 0, 0, 0, 1, 0) -- ThAt''S alL I gOt!
     ,(38, 438, 3, 0, 0, 0, 1, 0) -- YoU''rE tiCkinG me ofF!
     ,(39, 439, 4, 0, 0, 0, 1, 0) -- I aiN''t woRth moRe.


      -- Spider
     ,(40, 440, 1, 1, 0, 0, 0, 0) -- "The %NAME% dangles its %ITEM% at you."
     ,(41, 441, 2, 1, 0, 0, 0, 0)  -- "The %NAME% cautiously nudges its %ITEM% towards you."
     ,(42, 442, 3, 1, 0, 0, 0, 0) -- "The %NAME% throws out its %ITEM%."
     ,(43, 443, 4, 1, 0, 0, 0, 0) -- "The %NAME% drops its %ITEM% in front of you."

     ,(44, 444, 1, 0, 1, 0, 0, 0) -- "The %NAME% is smirking as it presents its %ITEM%."
     ,(45, 445, 2, 0, 1, 0, 0, 0) -- "The %NAME% seems distressed as it pushes forth its %ITEM%."
     ,(46, 446, 3, 0, 1, 0, 0, 0) -- "The %NAME% throws out its %ITEM% with mighty force."
     ,(47, 447, 4, 0, 1, 0, 0, 0) -- "The %NAME% appears unmoving as its %ITEM% is brought forth."

     ,(48, 448, 1, 0, 1, 0, 0, 0) -- "The %NAME%'s curious glare continues as a %ITEM% comes out."
     ,(49, 449, 2, 0, 1, 0, 0, 0) -- "The %NAME%'s presentations continue cautiously as it presents its %ITEM%."
     ,(50, 450, 3, 0, 1, 0, 0, 0) -- "The %NAME% seems to hesitate with fury before unveiling its %ITEM%."
     ,(51, 451, 4, 0, 1, 0, 0, 0) -- "%ITEM% is dragged out by the %NAME%."

     -- Spider / Accept
     ,(52, 452, 1, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(53, 452, 2, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(54, 452, 3, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(55, 452, 4, 0, 0, 0, 0, 1) -- The spider takes its leave...

     -- Spider / Reject
     ,(56, 453, 1, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(57, 453, 2, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(58, 453, 3, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(59, 453, 4, 0, 0, 0, 1, 0) -- The spider has become antagonistic!


      -- Zombie
     ,(60, 460, 1, 1, 0, 0, 0, 0) -- "Hrgh... Funny... Have spare %ITEM%."
     ,(61, 461, 2, 1, 0, 0, 0, 0) -- "Grah... Take %ITEM%... please."
     ,(62, 462, 3, 1, 0, 0, 0, 0) -- "Burrr... Take %ITEM%... leave."
     ,(63, 463, 4, 1, 0, 0, 0, 0) -- "%ITEM% worth more... than me."

     ,(64, 464, 1, 0, 1, 0, 0, 0) -- "Grah... Can... give %ITEM%."
     ,(65, 465, 2, 0, 1, 0, 0, 0) -- "What about... %ITEM%? Hrgh..."
     ,(66, 466, 3, 0, 1, 0, 0, 0) -- "Gruuuh... Take %ITEM%."
     ,(67, 467, 4, 0, 1, 0, 0, 0) -- "Hrrr... %ITEM%... is all I... can give."

     ,(68, 468, 1, 0, 1, 0, 0, 0) -- "Might have... Urgh... %ITEM%."
     ,(69, 469, 2, 0, 1, 0, 0, 0) -- "%ITEM%... maybe? Guh..."
     ,(70, 470, 3, 0, 1, 0, 0, 0) -- "%ITEM%... Burgh... Take."
     ,(71, 471, 4, 0, 1, 0, 0, 0) -- "Buuuh... %ITEM%."

      -- Zombie / Accept
     ,(72, 472, 1, 0, 0, 0, 0, 1) -- Enjoy like... I did.
     ,(73, 473, 2, 0, 0, 0, 0, 1) -- Please... have.
     ,(74, 474, 3, 0, 0, 0, 0, 1) -- Go... now.
     ,(75, 475, 4, 0, 0, 0, 0, 1) -- Uuuggghhh...

      -- Zombie / Reject
     ,(76, 476, 1, 0, 0, 0, 1, 0) -- Burgh! Fight... instead.
     ,(77, 477, 2, 0, 0, 0, 1, 0) -- Gargh! No have.
     ,(78, 478, 3, 0, 0, 0, 1, 0) -- ...Give me. Bargh!
     ,(79, 479, 4, 0, 0, 0, 1, 0) -- No... more. Grrruh!
;