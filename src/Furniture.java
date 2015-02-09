public class Furniture {
	public Position position = new Position();
	public String catalogId;
	public double width;
	public double height;
	public double depth;

	public Position getRelative(Furniture f) {
		Position rel = new Position();
		rel.setRot(position.getRot()-f.position.getRot());
		double X=position.posX-f.position.posX;
		double Y=position.posY-f.position.posY;
		rel.posX=Math.cos(f.position.getRot())*X+Math.sin(f.position.getRot())*Y;
		rel.posY=Math.cos(f.position.getRot())*Y-Math.sin(f.position.getRot())*X;
		return rel;
	}

	@Override
	public String toString() {
		return "CatalogId=" + catalogId + " width=" + width + " height="
				+ height + "depth=" + depth +" "+ position;
	}
	
	@Override
	public boolean equals(Object o){
		return ((Furniture)o).catalogId.equals(this.catalogId);
	}
	
	@Override
	public int hashCode(){
		return catalogId.hashCode();
	}
}
