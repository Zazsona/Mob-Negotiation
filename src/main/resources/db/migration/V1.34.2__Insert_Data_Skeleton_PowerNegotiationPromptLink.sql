INSERT INTO PowerNegotiationPromptLink (PowerNegotiationPromptId, PowerNegotiationResponseId, PersonalityId, NextPowerNegotiationPromptId, FollowOnSuccess, FollowOnFailure)
VALUES
-- YoU waNtiN' mY poWeR jus' liKe tHat? YoU'lL hAve tO woo mE fIrsT!
    -- Hand it over.
         (300, 0, 1, 301, 1, 0) -- AlrIghT, aLrigHt, I gEt iT.
            ,(301, null, -1, 325, 1, 1)
            ,(301, null, -1, 350, 1, 1)
        ,(300, 0, 2, 302, 1, 0) -- Ah...
            ,(302, null, -1, 325, 1, 1)
            ,(302, null, -1, 350, 1, 1)
        ,(300, 0, 3, 303, 1, 0) -- YoU doN't bEaT aRouNd tHe buSh, hUh?
            ,(303, null, -1, 325, 1, 1)
            ,(303, null, -1, 350, 1, 1)
        ,(300, 0, 4, 304, 1, 0) -- MaYbE...
            ,(304, null, -1, 325, 1, 1)
            ,(304, null, -1, 350, 1, 1)

         (300, 0, 1, 305, 0, 1) -- NAH'!
        ,(300, 0, 2, 306, 0, 1) -- CaN'T!
        ,(300, 0, 3, 307, 0, 1) -- TaKe a HiKe!
        ,(300, 0, 4, 308, 0, 1) -- CaN'T be BotHerEd!
    -- I'm no good at that.
         (300, 1, 1, 309, 1, 0) -- Ha! YoU loOk iT!
            ,(309, null, -1, 325, 1, 1)
            ,(309, null, -1, 350, 1, 1)
        ,(300, 1, 2, 310, 1, 0) -- YoU aNd mE bOtH...
            ,(310, null, -1, 325, 1, 1)
            ,(310, null, -1, 350, 1, 1)
        ,(300, 1, 3, 311, 1, 0) -- So mUcH fOr cHivAlrY.
            ,(311, null, -1, 325, 1, 1)
            ,(311, null, -1, 350, 1, 1)
        ,(300, 1, 4, 312, 1, 0) -- Me NeiThEr.
            ,(312, null, -1, 325, 1, 1)
            ,(312, null, -1, 350, 1, 1)

         (300, 1, 1, 313, 0, 1) -- SHaMe!
        ,(300, 1, 2, 314, 0, 1) -- Oh... gUeSs we'lL hAvE tO fIghT tHen.
        ,(300, 1, 3, 315, 0, 1) -- SoUndS lIkE yOu jUs' cAn'T be BotHerEd!
        ,(300, 1, 4, 316, 0, 1) -- Oh, yOu jUs' doN't LiKe mE!
    -- How about my place next Saturday? <3
         (300, 2, 1, 317, 1, 0) -- I'lL bRiNg tHiS siCk boDy.
            ,(317, null, -1, 325, 1, 1)
            ,(317, null, -1, 350, 1, 1)
        ,(300, 2, 2, 318, 1, 0) -- Oh! Su- SuRe... HaHa, jUs' joKin'!
            ,(318, null, -1, 325, 1, 1)
            ,(318, null, -1, 350, 1, 1)
        ,(300, 2, 3, 319, 1, 0) -- ... THat'S a jOke, rIghT?
            ,(319, null, -1, 325, 1, 1)
            ,(319, null, -1, 350, 1, 1)
        ,(300, 2, 4, 320, 1, 0) -- I mIghT be BuSy...
            ,(320, null, -1, 325, 1, 1)
            ,(320, null, -1, 350, 1, 1)

         (300, 2, 1, 321, 0, 1) -- YoU'Re juS' nOt my tYpe!
        ,(300, 2, 2, 322, 0, 1) -- SoRrY, yoU'rE nOt reAllY my tyPe...
        ,(300, 2, 3, 323, 0, 1) -- No chAncE, sQuiShy gUy.
        ,(300, 2, 4, 324, 0, 1) -- DoN't paTronIse Me.
-- C'mOn thOugh. WHat'S yoUr tYpe?
    -- Oh, you know...
         (325, 3, 1, 326, 1, 0) -- HeH, oHhHhhHhh. I sEe.
            ,(326, null, -1, 525, 1, 1)
        ,(325, 3, 2, 327, 1, 0) -- YeAh...
            ,(327, null, -1, 526, 1, 1)
        ,(325, 3, 3, 328, 1, 0) -- ProBaBlY.
            ,(328, null, -1, 527, 1, 1)
        ,(325, 3, 4, 329, 1, 0) -- YeaH... GuesS I dO...
            ,(329, null, -1, 528, 1, 1)

         (325, 3, 1, 330, 0, 1) -- WhaT? SlImeS? SilVerFisH? If yoU'rE noT spIttIng it oUt, I'lL spiT it OutTa yoU!
        ,(325, 3, 2, 331, 0, 1) -- NoT reaLly...
        ,(325, 3, 3, 332, 0, 1) -- NoPe!
        ,(325, 3, 4, 333, 0, 1) -- I doN't kNoW noThiN'.
    -- Slimes.
         (325, 4, 1, 334, 1, 0) -- Ha! GoOd oNe!
            ,(334, null, -1, 525, 1, 1)
        ,(325, 4, 2, 335, 1, 0) -- YoU'rE braVe tO aDmiT thAt...
            ,(335, null, -1, 526, 1, 1)
        ,(325, 4, 3, 336, 1, 0) -- To eAcH theIr oWn.
            ,(336, null, -1, 527, 1, 1)
        ,(325, 4, 4, 337, 1, 0) -- ... AlrIghT.
            ,(337, null, -1, 528, 1, 1)

         (325, 4, 1, 338, 0, 1) -- Ha! HaHa! HaHaHaHaHaHa!!!
        ,(325, 4, 2, 339, 0, 1) -- I waS seRioUs...
        ,(325, 4, 3, 430, 0, 1) -- DoN'T piSs mE abOuT.
        ,(325, 4, 4, 431, 0, 1) -- ...NoT quIte sKelEtoNs, thEn...
    -- Fire Type, it's strong against Grass.
         (325, 5, 1, 342, 1, 0) -- GoOd pIck!
            ,(342, null, -1, 525, 1, 1)
        ,(325, 5, 2, 343, 1, 0) -- HeY, me Too!
            ,(343, null, -1, 526, 1, 1)
        ,(325, 5, 3, 344, 1, 0) -- NoT quIte whAt I meAnt, bUt suRe.
            ,(344, null, -1, 527, 1, 1)
        ,(325, 5, 4, 345, 1, 0) -- YeAh, I gueSs...
            ,(345, null, -1, 528, 1, 1)

         (325, 5, 1, 346, 0, 1) -- Ah, I sEe yoU're jUsT a lOneR!
        ,(325, 5, 2, 347, 0, 1) -- ArE yoU mOckIng mE?
        ,(325, 5, 3, 348, 0, 1) -- YoU idIoT.
        ,(325, 5, 4, 349, 0, 1) -- UghhhHhhhHhh...
-- C'mOn thOugh, iF yOu caN't wOo mE I knOw all sOrts of gOod loOkerS I cAn intRoduCe yoU to!
    -- I'm not interested.
         (350, 6, 1, 351, 1, 0) -- THat's tHe spIriT!
            ,(351, null, -1, 525, 1, 1)
        ,(350, 6, 2, 352, 1, 0) -- HeH... FaIr enOugh.
            ,(352, null, -1, 526, 1, 1)
        ,(350, 6, 3, 353, 1, 0) -- YoU'vE gOt cOnviCtiOn, I sEe.
            ,(353, null, -1, 527, 1, 1)
        ,(350, 6, 4, 354, 1, 0) -- NeitHeR, reAllY...
            ,(354, null, -1, 528, 1, 1)

         (350, 6, 1, 355, 0, 1) -- BiT of A sqUarE, arEn't yOu?
        ,(350, 6, 2, 356, 0, 1) -- AlRighT, tHen. I wAs Just tRyiN' to Be nIce.
        ,(350, 6, 3, 357, 0, 1) -- ThEn wE havE ouRselVes nO furTher buSinesS.
        ,(350, 6, 4, 358, 0, 1) -- WhAteVer...
    -- You're trying way too hard.
         (350, 7, 1, 359, 1, 0) -- AheHeh. MaYbe. ThIs pIle of BonEs cAn't kEeP uP wIth wHat thE liViNg WanT.
            ,(359, null, -1, 525, 1, 1)
        ,(350, 7, 2, 360, 1, 0) -- YoU thInK sO?
            ,(360, null, -1, 526, 1, 1)
        ,(350, 7, 3, 361, 1, 0) -- DoN't We aLl?
            ,(361, null, -1, 527, 1, 1)
        ,(350, 7, 4, 362, 1, 0) -- ThAt'S a nEw oNe fOr me, hEh...
            ,(362, null, -1, 528, 1, 1)

         (350, 7, 1, 363, 0, 1) -- WhAt? YoU doN't tHinK I got wHat I'm pRomIsiN'?
        ,(350, 7, 2, 364, 0, 1) -- Oh... OkAy...
        ,(350, 7, 3, 365, 0, 1) -- I'lL shOw yoU tRying hArd!
        ,(350, 7, 4, 366, 0, 1) -- NoT eVen mY beSt iS eNouGh... lEt's eNd thIs.
    -- Yes! Let's go!
         (350, 8, 1, 367, 1, 0) -- YoU'rE aS fuN as yOu aRe gUllIble. HaHa!
            ,(367, null, -1, 525, 1, 1)
        ,(350, 8, 2, 368, 1, 0) -- YoU'rE rEallY inTereSted iN my fRienDs?
            ,(368, null, -1, 526, 1, 1)
        ,(350, 8, 3, 369, 1, 0) -- At LeasT I kNow yOu tRust Me, eVen if yOur hEad iS empTy.
            ,(369, null, -1, 527, 1, 1)
        ,(350, 8, 4, 370, 1, 0) -- Ha... ThaT woUld be tHe dReaM.
            ,(370, null, -1, 528, 1, 1)

         (350, 8, 1, 371, 0, 1) -- HaHaaaa! I gOt yoU gOod. NoW let'S rEalLy plAy.
        ,(350, 8, 2, 372, 0, 1) -- Oh... Uh I dOn'T acTualLy hAvE anYonE... SoRrY.
        ,(350, 8, 3, 373, 0, 1) -- YoU FOoL!
        ,(350, 8, 4, 374, 0, 1) -- Ugh... YOu arE DenSe.







-- I'lL neEd sOmE lunCh firSt. Can wE taKe a bReaK anD go eAt soMethIng?
    -- I'm not hungry.
         (375, 9, 1, 376, 1, 0) -- YoU beEn snacKing? C'moN, let'S tHinK abOuT thIs.
            ,(376, null, -1, 400, 1, 1)
            ,(376, null, -1, 425, 1, 1)
        ,(375, 9, 2, 377, 1, 0) -- CaN We gO gEt soMethiNg foR mE aT leaSt?
            ,(377, null, -1, 400, 1, 1)
            ,(377, null, -1, 425, 1, 1)
        ,(375, 9, 3, 378, 1, 0) -- We'Ll gEt soMe jUst fOr mE, thEn...
            ,(378, null, -1, 400, 1, 1)
        ,(375, 9, 4, 379, 1, 0) -- GuEsS it'lL be a tAble fOr oNe...
            ,(379, null, -1, 400, 1, 1)
            ,(379, null, -1, 425, 1, 1)

         (375, 9, 1, 380, 0, 1) -- WeLl, I'lL mAke it A taBle foR onE, thEn!
        ,(375, 9, 2, 381, 0, 1) -- Oh... OkAy...
        ,(375, 9, 3, 382, 0, 1) -- FiNe, thEn.
        ,(375, 9, 4, 383, 0, 1) -- I'lL kilL mY oWn foOd thEn.
    -- Only if we split the foraging.
         (375, 10, 1, 384, 1, 0) -- SuRe, 70/30!
            ,(384, null, -1, 400, 1, 1)
            ,(384, null, -1, 425, 1, 1)
        ,(375, 10, 2, 385, 1, 0) -- AlRigHt, sOunDs faIr!
            ,(385, null, -1, 400, 1, 1)
            ,(385, null, -1, 425, 1, 1)
        ,(375, 10, 3, 386, 1, 0) -- WoRks fOr mE.
            ,(386, null, -1, 400, 1, 1)
            ,(386, null, -1, 425, 1, 1)
        ,(375, 10, 4, 387, 1, 0) -- NoThiN' coMes fRee, I guEss.
            ,(387, null, -1, 400, 1, 1)
            ,(387, null, -1, 425, 1, 1)

         (375, 10, 1, 388, 0, 1) -- Or I CoUlD splIt yoU!
        ,(375, 10, 2, 389, 0, 1) -- Oh... nO thanKs.
        ,(375, 10, 3, 390, 0, 1) -- YoU cAn'T spRinG thAt oN me nOw!
        ,(375, 10, 4, 391, 0, 1) -- I thOughT it'D be oN yoU...
    -- Let's grab something.
         (375, 11, 1, 392, 1, 0) -- I'lL tAke aNythIn'!
            ,(392, null, -1, 400, 1, 1)
            ,(392, null, -1, 425, 1, 1)
        ,(375, 11, 2, 393, 1, 0) -- I'm nOt fUsSy!
            ,(393, null, -1, 400, 1, 1)
            ,(393, null, -1, 425, 1, 1)
        ,(375, 11, 3, 394, 1, 0) -- Now wE'rE geTtinG soMewheRe.
            ,(394, null, -1, 400, 1, 1)
            ,(394, null, -1, 425, 1, 1)
        ,(375, 11, 4, 395, 1, 0) -- SuRe...
            ,(395, null, -1, 400, 1, 1)
            ,(395, null, -1, 425, 1, 1)

         (375, 11, 1, 396, 0, 1) -- I'lL gRab yoUr flEsh!
        ,(375, 11, 2, 397, 0, 1) -- I thOughT you'D haVe soMethIng...
        ,(375, 11, 3, 398, 0, 1) -- GiVe me WhaT yoU haVe iNstEaD.
        ,(375, 11, 4, 399, 0, 1) -- Ugh... CaN't be BothEred. GivE me yoUrs.
-- AlrIghT, whAt dO yoU wAnt tO eaT, thEn?
    -- Anything.
         (400, 12, 1, 401, 1, 0) -- HeH, hoW abOut soMe miLk? CalCium's gOod foR thEse bOnes!
            ,(401, null, -1, 525, 1, 1)
        ,(400, 12, 2, 402, 1, 0) -- If yoU sAy sO...
            ,(402, null, -1, 526, 1, 1)
        ,(400, 12, 3, 403, 1, 0) -- I'lL picK thEn. I wAs thInKinG aboUt sOmE pIe.
            ,(403, null, -1, 527, 1, 1)
        ,(400, 12, 4, 404, 1, 0) -- YeAh, I'd eAt diRt.
            ,(404, null, -1, 528, 1, 1)

         (400, 12, 1, 405, 0, 1) -- I fiGurE yoU'll Do NiceLy.
        ,(400, 12, 2, 406, 0, 1) -- I thOughT you hAd a pLaN...
        ,(400, 12, 3, 407, 0, 1) -- ShouLda piCked soMethin', fleShbaG.
        ,(400, 12, 4, 408, 0, 1) -- ArE yoU eVen stIll IntErestEd?
    -- I've got this dirt block...
         (400, 13, 1, 409, 1, 0) -- SouNds scRummY!
            ,(409, null, -1, 525, 1, 1)
        ,(400, 13, 2, 410, 1, 0) -- GuEsS it'lL do...
            ,(410, null, -1, 526, 1, 1)
        ,(400, 13, 3, 411, 1, 0) -- UgH, foOd's fooD.
            ,(411, null, -1, 527, 1, 1)
        ,(400, 13, 4, 412, 1, 0) -- It'S jUst tHe soRt of thIng I'm Worth...
            ,(412, null, -1, 528, 1, 1)

         (400, 13, 1, 413, 0, 1) -- HaHa, I coUld dO betTer thAn thAt!
        ,(400, 13, 2, 414, 0, 1) -- Oh... I wAs expeCtin' SomethiNg bettEr.
        ,(400, 13, 3, 415, 0, 1) -- YoU caN do BetTer thAn thAt.
        ,(400, 13, 4, 416, 0, 1) -- ThAt's nOt woRth eaTing...
    -- Cake.
         (400, 14, 1, 417, 1, 0) -- NoW we'Re talKin'. It'S a PartY!
            ,(417, null, -1, 525, 1, 1)
        ,(400, 14, 2, 418, 1, 0) -- ThAt'd be Nice...
            ,(418, null, -1, 526, 1, 1)
        ,(400, 14, 3, 419, 1, 0) -- ThEn it'S setTleD!
            ,(419, null, -1, 527, 1, 1)
        ,(400, 14, 4, 420, 1, 0) -- YeaH, soUnds gOod...
            ,(420, null, -1, 528, 1, 1)

         (400, 14, 1, 421, 0, 1) -- I RecKon I'lL kiLl you For aN eXtra slIce!
        ,(400, 14, 2, 422, 0, 1) -- I... doN't lIke caKe.
        ,(400, 14, 3, 423, 0, 1) -- YoU haVe no CultUre, skinBod.
        ,(400, 14, 4, 424, 0, 1) -- ARe yoU tryIn' to Make thIs a paRty? No thAnks.
-- LeT's plAy a gaMe then, gueSs what I wAnnA eat!
    -- You don't look like you can eat...
         (425, 15, 1, 426, 1, 0) -- FaIr poInT!
            ,(426, null, -1, 525, 1, 1)
        ,(425, 15, 2, 427, 1, 0) -- YeAh, thanKs foR notiCin'.
            ,(427, null, -1, 526, 1, 1)
        ,(425, 15, 3, 428, 1, 0) -- AlriGht, EinSteiN. Well pLayed.
            ,(428, null, -1, 527, 1, 1)
        ,(425, 15, 4, 429, 1, 0) -- I uSed tO, thOugh. JuSt liKe yoU.
            ,(429, null, -1, 528, 1, 1)

         (425, 15, 1, 430, 0, 1) -- ThAt's nOt hOw you Play thE game!
        ,(425, 15, 2, 431, 0, 1) -- ThAnkS foR poIntIng it oUt mE...
        ,(425, 15, 3, 432, 0, 1) -- DUH!
        ,(425, 15, 4, 433, 0, 1) -- I kNow...
    -- Something high in calcium.
         (425, 16, 1, 434, 1, 0) -- AwhhHhhh yIsssSsss.
            ,(434, null, -1, 525, 1, 1)
        ,(425, 16, 2, 435, 1, 0) -- YEah, I gueSs so...
            ,(435, null, -1, 526, 1, 1)
        ,(425, 16, 3, 436, 1, 0) -- That'D do Me weLl.
            ,(436, null, -1, 527, 1, 1)
        ,(425, 16, 4, 437, 1, 0) -- MiGht lIft my SpritS...
            ,(437, null, -1, 528, 1, 1)

         (425, 16, 1, 438, 0, 1) -- YuP! YoU ouGht To do!
        ,(425, 16, 2, 439, 0, 1) -- Is thAt suPposeD to be A joke?
        ,(425, 16, 3, 440, 0, 1) -- No.
        ,(425, 16, 4, 441, 0, 1) -- UgghHhhHhh. DoN't BorE me wiTh thAt oNe.
    -- Shut up.
         (425, 17, 1, 442, 1, 0) -- NoW thAt's a sPicY rEsponSe!
            ,(442, null, -1, 525, 1, 1)
        ,(425, 17, 2, 443, 1, 0) -- Oh... SoRrY! JuSt wAntEd a bIt oF fUn, bUt I gEt iT...
            ,(443, null, -1, 526, 1, 1)
        ,(425, 17, 3, 444, 1, 0) -- HeH, wAnnA geT tO tHe pOinT, hUh?
            ,(444, null, -1, 527, 1, 1)
        ,(425, 17, 4, 445, 1, 0) -- PrEfEr a Bit oF siLenCe toO, hUh?
            ,(445, null, -1, 528, 1, 1)

         (425, 17, 1, 446, 0, 1) -- AwhHh. YoU'rE noT as Fun as I thOughT you'D be.
        ,(425, 17, 2, 447, 0, 1) -- FiNe...
        ,(425, 17, 3, 448, 0, 1) -- DoN't giVe me thAt lip!
        ,(425, 17, 4, 449, 0, 1) -- YoU'rE toO cockY.




-- You wAnt to taKe from Me whIle I'm DowN? DoEs that nOt boTher YouR coNsciEnce?
    -- Now that you mention it...
         (450, 18, 1, 451, 1, 0) -- YoU cErtaInly loOk boThereD!
            ,(451, null, -1, 475, 1, 1)
            ,(451, null, -1, 500, 1, 1)
        ,(450, 18, 2, 452, 1, 0) -- YeaH...
            ,(452, null, -1, 475, 1, 1)
            ,(452, null, -1, 500, 1, 1)
        ,(450, 18, 3, 453, 1, 0) -- YoU shOulD thInk tHingS thrOugh in fUture.
            ,(453, null, -1, 475, 1, 1)
            ,(453, null, -1, 500, 1, 1)
        ,(450, 18, 4, 454, 1, 0) -- We'rE all JusT miStakeS liKe thAt...
            ,(454, null, -1, 475, 1, 1)
            ,(454, null, -1, 500, 1, 1)

         (450, 18, 1, 455, 0, 1) -- GooD thiNg I don't hAve a ConscienCe!
        ,(450, 18, 2, 456, 0, 1) -- TimE to... FLEE!
        ,(450, 18, 3, 457, 0, 1) -- ShouLda thoUght abouT it bEfore, bagGybraIns.
        ,(450, 18, 4, 458, 0, 1) -- YoU don'T evEn know...
    -- ...Nope
         (450, 19, 1, 459, 1, 0) -- DoN't thInK iT'd Much boTher mIne eiTher.
            ,(459, null, -1, 475, 1, 1)
            ,(459, null, -1, 500, 1, 1)
        ,(450, 19, 2, 460, 1, 0) -- At leAst yOu'rE hoNest...
            ,(460, null, -1, 475, 1, 1)
            ,(460, null, -1, 500, 1, 1)
        ,(450, 19, 3, 461, 1, 0) -- At leAst yOu'rE hoNest.
            ,(461, null, -1, 475, 1, 1)
            ,(461, null, -1, 500, 1, 1)
        ,(450, 19, 4, 462, 1, 0) -- ShoUlda eXpecTed aS muCh...
            ,(462, null, -1, 475, 1, 1)
            ,(462, null, -1, 500, 1, 1)

         (450, 19, 1, 463, 0, 1) -- GoOd. ThIs woN't wEigh oN mIne.
        ,(450, 19, 2, 464, 0, 1) -- YoU... You'Re moRe hEartLesS thAn mE! And I doN't eVen haVe oNe no mOrE!
        ,(450, 19, 3, 465, 0, 1) -- YoU neEd tEacHinG a lEssOn!
        ,(450, 19, 4, 466, 0, 1) -- Oh... RiGht...
    -- It needs to be done.
         (450, 20, 1, 467, 1, 0) -- YeAh, yeaH. WhatEver.
            ,(467, null, -1, 475, 1, 1)
            ,(467, null, -1, 500, 1, 1)
        ,(450, 20, 2, 468, 1, 0) -- YoU... thInk So?
            ,(468, null, -1, 475, 1, 1)
            ,(468, null, -1, 500, 1, 1)
        ,(450, 20, 3, 469, 1, 0) -- SuPpose we aLl haVe ouR tiMe...
            ,(469, null, -1, 475, 1, 1)
            ,(469, null, -1, 500, 1, 1)
        ,(450, 20, 4, 470, 1, 0) -- I KnoW...
            ,(470, null, -1, 475, 1, 1)
            ,(470, null, -1, 500, 1, 1)

         (450, 20, 1, 471, 0, 1) -- I doN't tHinK sO!
        ,(450, 20, 2, 472, 0, 1) -- I'm nOt reAdy! NoT yeT!
        ,(450, 20, 3, 473, 0, 1) -- YoU'rE riGht. TiMe to End yOu.
        ,(450, 20, 4, 474, 0, 1) -- I dOn'T caRe muCh for YouR naRratiVe.
-- ThiS couLda bEen the OthEr waY arOunD, y'kNow?
    -- No chance.
         (475, 21, 1, 476, 1, 0) -- I'm LikiN' The MacHo aCt!
            ,(476, null, -1, 525, 1, 1)
        ,(475, 21, 2, 477, 1, 0) -- I GueSs So, I'vE nOt goT the SkilLs I uSed tO.
            ,(477, null, -1, 526, 1, 1)
        ,(475, 21, 3, 478, 1, 0) -- AlrIghT, alRighT.
            ,(478, null, -1, 527, 1, 1)
        ,(475, 21, 4, 479, 1, 0) -- YoU'rE riGht...
            ,(479, null, -1, 528, 1, 1)

         (475, 21, 1, 480, 0, 1) -- We'Ll seE aboUt thAt!
        ,(475, 21, 2, 481, 0, 1) -- NaH', I wOn'T falL thAt eaSily.
        ,(475, 21, 3, 482, 0, 1) -- I diSagRee.
        ,(475, 21, 4, 483, 0, 1) -- ThEn whY bOthEr TalkIng...?
    -- Want to swap?
         (475, 22, 1, 484, 1, 0) -- AhhHhh. ThE ol' SwitCheroO! YoU keEp thIngs YouR waY, for thIs tiMe.
            ,(484, null, -1, 525, 1, 1)
        ,(475, 22, 2, 485, 1, 0) -- YeAh... It'S oKay thOugh, yoU eaRneD thIs...
            ,(485, null, -1, 526, 1, 1)
        ,(475, 22, 3, 486, 1, 0) -- We'Re toO faR in nOw, buT thanKs.
            ,(486, null, -1, 527, 1, 1)
        ,(475, 22, 4, 487, 1, 0) -- TheRe'd bE nO poiNt noW...
            ,(487, null, -1, 528, 1, 1)

         (475, 22, 1, 488, 0, 1) -- YoU kiDdin'? I gOt the BesT angLe to kNock yoU doWn frOm heRe!
        ,(475, 22, 2, 489, 0, 1) -- DoN't TeaSe me...
        ,(475, 22, 3, 490, 0, 1) -- DoN't patRonise Me.
        ,(475, 22, 4, 491, 0, 1) -- WhaT'd bE the pOint!?
    -- Yet here we are.
         (475, 23, 1, 492, 1, 0) -- YeAh, YeaH. I gEt it.
            ,(492, null, -1, 525, 1, 1)
        ,(475, 23, 2, 493, 1, 0) -- ...SupPose thIs is wHat faTe had iN stoRe...
            ,(493, null, -1, 526, 1, 1)
        ,(475, 23, 3, 494, 1, 0) -- GoT tO deAl with It nOw thEn.
            ,(494, null, -1, 527, 1, 1)
        ,(475, 23, 4, 495, 1, 0) -- ThAt's jUst thE waY of tHe caRds...
            ,(495, null, -1, 528, 1, 1)

         (475, 23, 1, 496, 0, 1) -- LeT's chAnge thAt arOund!
        ,(475, 23, 2, 497, 0, 1) -- I... I'lL fiGht it!
        ,(475, 23, 3, 498, 0, 1) -- NoT foR loNg.
        ,(475, 23, 4, 499, 0, 1) -- FaTe haS someThing elSe in sTore for Us botH.
-- JuSt leT me teLl you, I Am nOt the oNe yoU shoUld detEst.
    -- You're right.
         (500, 24, 1, 501, 1, 0) -- YeAh, yOu knOw it. I'm aLwaYs rIgHt.
            ,(501, null, -1, 525, 1, 1)
        ,(500, 24, 2, 502, 1, 0) -- ThAnkS foR reAliSin'.
            ,(502, null, -1, 526, 1, 1)
        ,(500, 24, 3, 503, 1, 0) -- NoW you gEt it.
            ,(503, null, -1, 527, 1, 1)
        ,(500, 24, 4, 504, 1, 0) -- ThEre are bEingS thAt CeRtaiNly gEt a Lot Worse thAn me...
            ,(504, null, -1, 528, 1, 1)

         (500, 24, 1, 505, 0, 1) -- ThAnkIn' yOu, I thInk my HatRed oF yoUr muG iS weLl pLacEd, thOugh!
        ,(500, 24, 2, 506, 0, 1) -- ...
        ,(500, 24, 3, 507, 0, 1) -- ShOuLda picKed up on ThAt frOm thE stArt.
        ,(500, 24, 4, 508, 0, 1) -- BuT stIll, I wiSh fOr yoU to End mE.
    -- That's absurd.
         (500, 25, 1, 509, 1, 0) -- NiCe to See eVen the New kiDs on tHe blOck kEep up wIth tHe pRejuDice.
            ,(509, null, -1, 525, 1, 1)
        ,(500, 25, 2, 510, 1, 0) -- ...
            ,(510, null, -1, 526, 1, 1)
        ,(500, 25, 3, 511, 1, 0) -- LeT uS deTesT eaCh otHer eqUallY, thEn.
            ,(511, null, -1, 527, 1, 1)
        ,(500, 25, 4, 512, 1, 0) -- LoOks liKe thAt's tHe way It'S goTta be, huH?
            ,(512, null, -1, 528, 1, 1)

         (500, 25, 1, 513, 0, 1) -- JuSt liKe yoUr chAnces of tAkinG me dOwn!
        ,(500, 25, 2, 514, 0, 1) -- Oh, RigHt...
        ,(500, 25, 3, 515, 0, 1) -- YeAh, it Is. I juSt wAnted yoU to sHut uP for a Bit!
        ,(500, 25, 4, 516, 0, 1) -- GuEss thEre'll nEvEr be pEacE. Let'S enD thiS.
    -- I'll be the judge of that.
         (500, 26, 1, 517, 1, 0) -- AlriGht, I'lL tAke thAt rolL of The DiCe.
            ,(517, null, -1, 525, 1, 1)
        ,(500, 26, 2, 518, 1, 0) -- I'lL leT yoU arrIve at yoUr oWn decSionS, thEn...
            ,(518, null, -1, 526, 1, 1)
        ,(500, 26, 3, 519, 1, 0) -- FiNe, jUst doN't MeSs me abOut.
            ,(519, null, -1, 527, 1, 1)
        ,(500, 26, 4, 520, 1, 0) -- No neEd to suGar coAt it...
            ,(520, null, -1, 528, 1, 1)

         (500, 26, 1, 521, 0, 1) -- I aiN't gOnnA waIt all Day foR yoU to mAke uP yoUr miNd.
        ,(500, 26, 2, 522, 0, 1) -- I doN't thInk I cAn taKe noT knOwing...
        ,(500, 26, 3, 523, 0, 1) -- I doN't tAke wEll tO suCh amBiguity.
        ,(500, 26, 4, 524, 0, 1) -- SoMetiMes it Pays to jUst comMit to hAtred, yoU knOw?
;