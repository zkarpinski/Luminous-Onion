// Interfaces matching the backend data model, sort of..
export interface OrgData {
  label: string;
}
export interface ProductData {
  id: number;
  label: string;
  name: string;
  org?: OrgData;
  description: string;
}
export interface SourceData {
  id: number;
  label: string;
  product?: ProductData;
}
export interface FindingData {
  id: number;
  name: string;
  source?: SourceData;
}
