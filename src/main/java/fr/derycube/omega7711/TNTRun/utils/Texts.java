package fr.derycube.omega7711.TNTRun.utils;

import lombok.Getter;

@Getter
public enum Texts {
    ModerationMode("Moderation Mode", "Mode Modération", "TRUK ANTYMECHEN"),
    Pseudo("Nick", "Pseudo", "Toaté"),
    hasdied(" has died.", " est mort.", " Ilé MORT."),
    TimeLeft("%s second%ss left before starting", "%s seconde%ss restante%ss avant le démarrage", "%s SEKOND%ss AVEN LA PARHTY"),
    Waitingforxplayers("Waiting for %s player(s)", "En attente de %s joueur(s)", "ON ATAN %s ZOOER(S)"),
    StartingIn("Starting in &e%s&fs", "Démarrage dans &e%s&fs", "SAKOMANSS DAN &e%s&fs"),
    Players("Players", "Joueurs", "JANS"),
    TestText("This is a test text", "Ceci est un texte de test", "SACET 1 MESAJ 2 TAIST"),
    Showing_Build("Showing time...", "Affichage des builds..", "TRUK ACONSTRUIR"),
    NeedToRebuild("Rebuilding time", "Reconstruction...", "FO REFER MTN"),
    Scanning("Scanning...", "Scan en cours...", "SA REUGARD"),
    Accuracy("Accuracy", "Exactitude", "LEUNIVO"),
    YouveGainedCoins("You have got &e&l%s ⛁.", "Vous avez reçu &r&l%s ⛁.", "TIHIN VOUALA &e%s SOU (⛁)."),
    GameOver("Game Over", "Partie Terminée", "Sé LA F1"),
    PlayerEliminated("&c%p&r died!", "&c%p&r est mort.", "&c%p&r ILE MOR!!!"),
    NoOneEliminated("&cNo one&r is eliminated, everyone as &c%s%&r of accuracy.", "&cPersonne&r n'est éliminé, tout le monde a &c%s%&r de précision.", "YA &cPERSAUNN&r KIHé MOR KAR TT LMONDA Fé &c%s%&r!!!"),
    URStats("Your stats", "Vos stats", "TéSTAT"),
    GameStartCancelled("The start of the game is cancelled, waiting for other players.", "Le démarrage de la partie a été annulé, en attente d'autres joueurs", "LEU JE IKOMMANSS PA MTN, FO DOTR JAN"),
    Yes("Yes", "Oui", "UI"),
    No("No", "Non", "NOP"),
    ReturnToLobby("Return to the Lobby", "Retourner au lobby", "REUTOOR MESON"),
    PlayerWonGame("&c&l%s&r won the game.", "&c&l%s&r a gagné la partie.", "&c&l%s&r ILE TRO FOR."),
    PlayersAlive("Players alive", "Joueurs en vie", "JAN KI REST"),
    YoureTNT("You are a TNT!", "Tu es une TNT!", "TUVA FER KABOOM"),
    YoureSafe("You are safe!", "Tu es safe!", "TOU É TRHENKIL"),
    Run("RUUUN!", "COOUUURS!", "FO KOURIR!!!!!!!!!"),
    BecameTNT("&c%p&f became a &4&lTNT&f!", "&c%p&f est devenu une &4&lTNT&f!", "&c%p&f IVA FAIR &4&lKABOOOOOOOM&f!!!!!!!!!!!!!!"),
    s("s", "s", "S"),
    inRound("In round", "En round", "EN JHEU"),
    points("Score", "Points", "TRUK JONE"),
    WinStreak("WinStreak", "WinStreak", "WIN DAFILET"),
    Unabletofindwhowon("Unable to find the player who won. Sorry.", "Impossible de trouver le joueur qui a gagné. Désolé.", "ALAUR LE GAR KIA GANIAI JSP OU YLé")
    ;
    public final String english;
    public final String french;
    public final String tkt;

    Texts(String english, String french, String tkt) {
        this.english = english;
        this.french = french;
        this.tkt = tkt;
    }
}
