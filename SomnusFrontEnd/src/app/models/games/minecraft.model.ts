import { GameModel } from "./game-model";

export interface MinecraftInfoModel extends GameModel {
    version: string;
    hostname: string;
    software: string;
    numOnlinePlayers: number;
    playerNames: string[];
    isOnline: boolean;
}