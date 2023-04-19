public class togruta_f.nem
public togruta_f.nem
texWidth = 150;
texHeight = 150;
right_arm = new ModelRenderer(this);
right_arm.setPos(-5.0F, 2.0F, 0.0F);
setRotationAngle(right_arm, 0.0F, 0.0F, 0.0F);
right_arm.texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3F, 12F, 4F, 0.0F, false);
left_leg = new ModelRenderer(this);
left_leg.setPos(1.9F, 12.0F, 0.0F);
setRotationAngle(left_leg, 0.0F, 0.0F, 0.0F);
left_leg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4F, 12F, 4F, 0.0F, false);
jacket = new ModelRenderer(this);
jacket.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(jacket, 0.0F, 0.0F, 0.0F);
jacket.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8F, 12F, 4F, 0.2F, false);
body = new ModelRenderer(this);
body.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(body, 0.0F, 0.0F, 0.0F);
body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8F, 12F, 4F, 0.0F, false);
chest = new ModelRenderer(this);
chest.setPos(0.0F, -0.1F, 1.0F);
body.addChild(chest);
setRotationAngle(chest, 0.0F, 0.0F, 0.0F);
chest.texOffs(0, 65).addBox(-3.0F, 2.0F, -4.0F, 6F, 3F, 2F, 0.0F, false);
head = new ModelRenderer(this);
head.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(head, 0.0F, 0.0F, 0.0F);
head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8F, 8F, 8F, 0.0F, false);
TailBaseR = new ModelRenderer(this);
TailBaseR.setPos(-1.4F, -10.1F, -2.6F);
head.addChild(TailBaseR);
setRotationAngle(TailBaseR, -0.22759093F, 0.14398967F, 0.0F);
TailBaseR.texOffs(0, 71).addBox(-4.0F, 0.0F, 0.0F, 4F, 7F, 6F, 0.0F, true);
TailMidR = new ModelRenderer(this);
TailMidR.setPos(-0.6F, 2.7F, 2.3F);
TailBaseR.addChild(TailMidR);
setRotationAngle(TailMidR, -0.13665928F, -0.13665928F, 0.0F);
TailMidR.texOffs(20, 71).addBox(-3.0F, 0.0F, 0.0F, 3F, 10F, 3F, 0.0F, true);
TailLowerR = new ModelRenderer(this);
TailLowerR.setPos(-0.5F, 10.0F, 0.5F);
TailMidR.addChild(TailLowerR);
setRotationAngle(TailLowerR, 0.31869712F, 0.0F, 0.0F);
TailLowerR.texOffs(32, 71).addBox(-2.0F, 0.0F, 0.0F, 2F, 6F, 2F, 0.0F, true);
TailUpperR = new ModelRenderer(this);
TailUpperR.setPos(-0.2F, -3.5F, 0.3F);
TailBaseR.addChild(TailUpperR);
setRotationAngle(TailUpperR, 0.13700834F, 0.0F, 0.13700834F);
TailUpperR.texOffs(41, 71).addBox(-3.0F, 0.0F, 0.0F, 3F, 5F, 3F, 0.0F, true);
TailTipR = new ModelRenderer(this);
TailTipR.setPos(-1.4F, -2.7F, 0.5F);
TailUpperR.addChild(TailTipR);
setRotationAngle(TailTipR, 0.0F, 0.0F, -0.31869712F);
TailTipR.texOffs(54, 72).addBox(-2.0F, 0.0F, 0.0F, 2F, 3F, 2F, 0.0F, true);
TailBaseB = new ModelRenderer(this);
TailBaseB.setPos(-3.0F, -7.7F, 0.4F);
head.addChild(TailBaseB);
setRotationAngle(TailBaseB, 0.0F, 0.0F, 0.0F);
TailBaseB.texOffs(10, 85).addBox(0.0F, 0.0F, 0.0F, 6F, 5F, 5F, 0.0F, false);
TailMidB = new ModelRenderer(this);
TailMidB.setPos(1.0F, -1.4F, 2.5F);
TailBaseB.addChild(TailMidB);
setRotationAngle(TailMidB, 0.0F, 0.0F, 0.0F);
TailMidB.texOffs(34, 81).addBox(0.0F, 0.0F, -0.9F, 4F, 11F, 3F, 0.0F, true);
TailLowerB = new ModelRenderer(this);
TailLowerB.setPos(1.0F, 8.8F, -0.4F);
TailMidB.addChild(TailLowerB);
setRotationAngle(TailLowerB, 0.0F, 0.0F, 0.0F);
TailLowerB.texOffs(49, 83).addBox(0.0F, 0.0F, 0.0F, 2F, 6F, 2F, 0.0F, true);
SkullElevation = new ModelRenderer(this);
SkullElevation.setPos(-4.0F, -9.1F, -2.0F);
head.addChild(SkullElevation);
setRotationAngle(SkullElevation, 0.0F, 0.0F, 0.0F);
SkullElevation.texOffs(60, 80).addBox(0.0F, 0.0F, 0.0F, 8F, 2F, 6F, 0.0F, false);
TailBaseL = new ModelRenderer(this);
TailBaseL.setPos(1.4F, -10.1F, -2.6F);
head.addChild(TailBaseL);
setRotationAngle(TailBaseL, -0.22759093F, -0.14398967F, 0.0F);
TailBaseL.texOffs(0, 71).addBox(0.0F, 0.0F, 0.0F, 4F, 7F, 6F, 0.0F, false);
TailMidL = new ModelRenderer(this);
TailMidL.setPos(0.6F, 2.7F, 2.3F);
TailBaseL.addChild(TailMidL);
setRotationAngle(TailMidL, -0.13665928F, 0.13665928F, 0.0F);
TailMidL.texOffs(20, 71).addBox(0.0F, 0.0F, 0.0F, 3F, 10F, 3F, 0.0F, false);
TailLowerL = new ModelRenderer(this);
TailLowerL.setPos(0.5F, 10.0F, 0.5F);
TailMidL.addChild(TailLowerL);
setRotationAngle(TailLowerL, 0.31869712F, 0.0F, 0.0F);
TailLowerL.texOffs(32, 71).addBox(0.0F, 0.0F, 0.0F, 2F, 6F, 2F, 0.0F, false);
TaiUpperL = new ModelRenderer(this);
TaiUpperL.setPos(0.2F, -3.5F, 0.3F);
TailBaseL.addChild(TaiUpperL);
setRotationAngle(TaiUpperL, 0.13700834F, 0.0F, -0.13700834F);
TaiUpperL.texOffs(41, 71).addBox(0.0F, 0.0F, 0.0F, 3F, 5F, 3F, 0.0F, false);
TaiTipL = new ModelRenderer(this);
TaiTipL.setPos(1.4F, -2.7F, 0.5F);
TaiUpperL.addChild(TaiTipL);
setRotationAngle(TaiTipL, 0.0F, 0.0F, 0.31869712F);
TaiTipL.texOffs(54, 72).addBox(0.0F, 0.0F, 0.0F, 2F, 3F, 2F, 0.0F, false);
left_sleeve = new ModelRenderer(this);
left_sleeve.setPos(5.0F, 2.0F, 0.0F);
setRotationAngle(left_sleeve, 0.0F, 0.0F, 0.0F);
left_sleeve.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3F, 12F, 4F, 0.2F, false);
right_sleeve = new ModelRenderer(this);
right_sleeve.setPos(-5.0F, 2.0F, 0.0F);
setRotationAngle(right_sleeve, 0.0F, 0.0F, 0.0F);
right_sleeve.texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3F, 12F, 4F, 0.2F, false);
left_arm = new ModelRenderer(this);
left_arm.setPos(5.0F, 2.0F, 0.0F);
setRotationAngle(left_arm, 0.0F, 0.0F, 0.0F);
left_arm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3F, 12F, 4F, 0.0F, false);
right_leg = new ModelRenderer(this);
right_leg.setPos(-1.9F, 12.0F, 0.0F);
setRotationAngle(right_leg, 0.0F, 0.0F, 0.0F);
right_leg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4F, 12F, 4F, 0.0F, false);
cloak = new ModelRenderer(this);
cloak.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(cloak, 0.0F, 0.0F, 0.0F);
ear = new ModelRenderer(this);
ear.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(ear, 0.0F, 0.0F, 0.0F);
right_pants = new ModelRenderer(this);
right_pants.setPos(-2.0F, 12.0F, 0.0F);
setRotationAngle(right_pants, 0.0F, 0.0F, 0.0F);
right_pants.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4F, 12F, 4F, 0.2F, false);
hat = new ModelRenderer(this);
hat.setPos(0.0F, 0.0F, 0.0F);
setRotationAngle(hat, 0.0F, 0.0F, 0.0F);
hat.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8F, 8F, 8F, 0.2F, false);
left_pants = new ModelRenderer(this);
left_pants.setPos(2.0F, 12.0F, 0.0F);
setRotationAngle(left_pants, 0.0F, 0.0F, 0.0F);
left_pants.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4F, 12F, 4F, 0.2F, false);
