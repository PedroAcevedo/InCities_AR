//
//  MasTagAnchor.h
//  MaxstARiOS
//
//  Created by keane on 21/01/2019.
//  Copyright © 2019 Maxst. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <simd/SIMD.h>

@interface MasTagAnchor : NSObject
{
    @public NSString* name;
    @public float positionX;
    @public float positionY;
    @public float positionZ;
    
    @public float rotationX;
    @public float rotationY;
    @public float rotationZ;
    
    @public float scaleX;
    @public float scaleY;
    @public float scaleZ;
    
    NSString* nameKey;
    NSString* positionXKey;
    NSString* positionYKey;
    NSString* positionZKey;
    
    NSString* rotationXKey;
    NSString* rotationYKey;
    NSString* rotationZKey;
    
    NSString* scaleXKey;
    NSString* scaleYKey;
    NSString* scaleZKey;
}

- (id)initWithData:(NSDictionary*)data;
- (NSString *)getName;
- (simd_float3)getPosition;
- (simd_float3)getRotation;
- (simd_float3)getScale;
- (void)setPosition:(simd_float3)position;
- (void)setRotation:(simd_float3)rotation;
- (void)setScale:(simd_float3)scale;
- (void)setName:(NSString *)name;
@end
