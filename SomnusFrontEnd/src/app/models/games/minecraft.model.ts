import { GameModel } from "./game-model";
import { MinecraftPlayer } from "./minecraft-player.model";

export interface MinecraftInfoModel extends GameModel {
    version: string;
    hostname: string;
    software: string;
    numOnlinePlayers: number;
    players: MinecraftPlayer[];
    isOnline: boolean;
}