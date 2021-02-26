import { Identifiable } from "../identifiable";

export interface SystemInfoModel extends Identifiable {
    numCpuThreads: number;
    totalMemory: number;
    cpuUsage: number;
    memoryUsage: number;
    swapSize: number;
    swapUsage: number;
    temperature: number;
    maxTemp?: number;
}