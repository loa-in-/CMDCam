package com.creativemd.cmdcam.client.mode;

import com.creativemd.cmdcam.common.utils.CamPath;
import com.creativemd.cmdcam.common.utils.CamPoint;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;

public class OutsideMode extends CamMode {
	
	public EntityLivingBase camPlayer;
	
	public OutsideMode(CamPath path) {
		super(path);
		if (path != null)
			this.camPlayer = new EntityZombie(mc.world);
	}
	
	@Override
	public CamMode createMode(CamPath path) {
		return new OutsideMode(path);
	}
	
	@Override
	public String getDescription() {
		return "the player isn't the camera, you can control him at every time";
	}
	
	@Override
	public void onPathFinish() {
		super.onPathFinish();
		mc.setRenderViewEntity(mc.player);
	}
	
	@Override
	public EntityLivingBase getCamera() {
		return camPlayer;
	}
	
	@Override
	public void processPoint(CamPoint point) {
		super.processPoint(point);
		
		mc.setRenderViewEntity(camPlayer);
		if (camPlayer instanceof EntityPlayer)
			((EntityPlayer) camPlayer).capabilities.isFlying = true;
		camPlayer.setPositionAndRotation(point.x, point.y, point.z, (float) point.rotationYaw, (float) point.rotationPitch);
		double playerEyeHeight = mc.player.getEyeHeight();
		double camEyeHeight = camPlayer.getEyeHeight();
		camPlayer.setLocationAndAngles(point.x, point.y - camPlayer.getEyeHeight() + mc.player.getEyeHeight(), point.z, (float) point.rotationYaw, (float) point.rotationPitch);
		camPlayer.setRotationYawHead((float) 0);
	}
	
}
